# Kotlin DSL for JSNAPy (experimental)

## Introduction

Many if not most automation tools use YAML these days. YAML advantages are well-known (relative simplicity, conciseness, 
human readability and editability, JSON compatibility, etc.). However, any good thing is only good until
its use turns into abuse (so to say). A very common situation that you've probably seen yourself looks as follows:
a huge YAML file is passed to a complicated software system (e.g. automation or provisioning tool) that
starts its work based on that YAML data. Then, because of some "wrong" setting in YAML file, the system throws an error
(or, even worse, the run completes, but the result looks broken). The problem is that it is often very hard 
to figure out what the initial issue was based on the visible symptoms like error messages and the location of 
where execution was terminated. Also, maintenance of 1000s-line YAML files is rarely a pleasant pastime. 

One of the reasons why this happens is because YAML defines a structure that is inherently dynamic; there is nothing
like formal XML schema or data model to constrain possible key values and nesting of lists and mappings. No type 
checking is performed both for primitive values or for nested structures. 

To add to this, systems often tend to work with mutable state - that is, the system does not work with original YAML 
data directly, but instead, some intermediate data structures are created, making it even harder to figure out what 
went wrong.

Shall we avoid using YAML then? We will see what happens in future, but for now it is clearly not possible
having in mind its widespreadness. Instead, one approach is to build required YAML files from a higher-level
DSL (domain specific language) that can check many of possible errors in the very beginning (before the
file is sent for actual processing). 

[Kotlin](https://kotlinlang.org/) language is very good at building internal DSLs. You can find a good introduction 
[here](https://medium.com/@antonarhipov/awesome-kotlin-domain-specific-languages-f1870be41b0), but let me show 
just one simple example from [Kotlin in Action](https://www.manning.com/books/kotlin-in-action) book: 
```kotlin
fun createSimpleTable() = createHTML().
    table {
        tr {
            td { +"cell" }
        }
    }
``` 
As you probably guessed, this code builds HTML table (using [kotlinx.html](https://github.com/Kotlin/kotlinx.html) 
library). For another cool example, visit [Kubernetes Kotlin DSL](https://github.com/fkorotkov/k8s-kotlin-dsl) 
project page.

## Kotlin DSL for JSNAPy - Project Details

It looks like many other tools can benefit from building a corresponding Kotlin DSL as well. In this repository
(work in progress) I build Kotlin DSL to generate YAML test files for [JSNAPy](https://github.com/Juniper/jsnapy), a 
popular tool for testing Junos OS-based networks. Here is an example of YAML test file that JSNAPy expects:
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
tests_include:
 - Test interface admin status
```
Now, how do you know that for example `rpc` and `kwargs` must belong to one list element, and `iterate` is the
next list element? Of course, from documentation and available examples. But if you make an error, the system 
does not warn you before you see a runtime error. Nothing like auto-completion is available when you edit
your YAML file in the editor.

Using DSL developed here, the test is rewritten as follows:
```kotlin
val myTestFile = createJSNAPyTestFile {
    jsnapyTest("Test interface admin status") {
        rpc = "get-interface-information"
        kwarg("terse", "True")
        iterate {
            xpath = "physical-interface"
            testClause {
                testop = "equals"
                values = "admin-status, up"
                info = "Test Succeeded for {{pre['name']}}"
                err = "Test Failed for {{pre['name']}}"
            }
        }
    }
    testsInclude {
        +"Test interface admin status"
    }
}
```   
This may look not look like a big deal, but the advantages of writing JSNAPy tests using Kotlin DSL include:
- Context-aware structure checking (what element is allowed in each hierarchy)
- Autocompletion, when working from IDE like IntelliJ IDEA
- Type checking
- Some extra semantic checks

As previously noted, this project is experimental and a work in progress; feel free to comment and contribute in
any way. For example you can create an issue, a pull request, or reach me at Twitter, 
[@JuniperTrain](https://twitter.com/JuniperTrain) handle.
