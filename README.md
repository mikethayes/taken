takeN
=====

This code is based on the article
[Batching Select Statements in JDBC|http://www.javaranch.com/journal/200510/Journal200510.jsp#a2] by [Jeanne Boyarsky|http://www.coderanch.com/how-to/java/ActiveStaff#JeanneBoyarsky]

##Batching
In JDBC, there's no batch call for select statements, so if you have a query of
the form 
    select x from y where id in ? 
you have to create a new statement
every time. i.e. you can't use a prepared statement. For extra vendor specific
excitement, Oracle limits the number of values in a subselect to 1000.
On top of this, you loose out on the database side because, unless you happen
to pass the same number of values every time, the database incurs the cost of
parsing the statement and creating an execution plan every time.
Jeannes solution breaks the list of values into predefined batches. You define
a range of batch sizes and pull an approproate batch size from the list of
values until there are no values left to extract.

For example, if I define my batch sizes as [1, 4, 11, 51] and I have 221 values
to query for, that breaks down into the following set of batches
    4 * 51 (204)
    1 * 11 ( 11)
    1 *  4 (  4)
    2 *  1 (  1)

The java code to do this is not that complicated but it feels
cumbersome, particularly if you have to implement it without using Guice,
commons collection or the like (how inhuman!).

But as a Clojure learning exercise, this seems perfect. 
