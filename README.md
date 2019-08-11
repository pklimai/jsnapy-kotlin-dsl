# Kotlin DSL for JSNAPy (experimental)

## Introduction

Many if not most automation tools use YAML these days. YAML advantages are well-known (relative simplicity, conciseness, 
human readability and editability, JSON compatibility, etc.). However, any good thing is only good until
its use turns into abuse (so to say). A very common situation that you've probably seen yourself looks as follows:
a huge YAML file is passed to a complicated software system (e.g. automation or provisioning tool) that
starts its work based on that YAML data. Then, because of some "wrong" setting in YAML file, the system throws an error
(or, even worse, the run completes, but the result looks broken). The problem is that it is often very hard 
to figure out what the initial issue was based on the visible symptoms like error messages
and the location of where execution was terminated. 

This happens because YAML defines a structure that is inherently dynamic; there is nothing like XML schema to constrain
possible key values and nesting of lists and mappings. No type checking is performed both for primitive
values or for nested structures. To add to this, systems often tend to work with mutable state - that
is, the system does not work with original YAML data directly, but instead, some intermediate data structures
are created, making it even harder to figure out what went wrong.

Shall we avoid using YAML then? We will see what happens in future, but for now it is clearly not possible
having in mind its widespreadness. Instead, one approach is to build required YAML files from a higher-level
DSL (domain specific language) that can check many of possible errors in the very beginning (before the
file is sent to processing). 

Kotlin language is very good at building DSLs. You can find more details ..here.., but just one simple example:
from ..this.. book: to build HTML table, you can use kotlinx.html library like this:
```kotlin

``` 

Here, I use Kotlin internal DSL to generate YAML files for JSNAPy, a popular tool for testing Junos OS-based
networks. Here is an example of YAML test file that JSNAPy expects:
```yaml
Test interface admin status:
  - rpc: get-interface-information
    kwargs:
      terse: True
  - iterate:
      xpath: physical-interface
      tests:
        - is-equal: admin-status, up
          info: "Test Succeeded for {{pre['name']}}"
          err: "Test Failed for {{pre['name']}}"
```
Now, how do you know that for example `rpc` and `kwargs` must belong to one list element, and `iterate` is the
next list element? Of course, from documentation and available examples. But if you make an error, the system 
does not correct you.

Using this project, the test is rewritten as follows:
```yaml

```   
This may look not very convincing, but