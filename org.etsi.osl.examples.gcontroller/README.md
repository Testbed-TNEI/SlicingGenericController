# Generic Controller example

OSL's Service orchestrator (OSOM) has the capability of contacting external controllers, given a specific resource category that this controller can manage. 

This project is an example of creating a generic controller, that registers two Resource Specifications for managing resources ResourceA and ResourceB. 

Listens for messages on queues as registered by category name and version for CREATE/UPD/DELETE, with this scheme:

- CREATE / category_name / version
- UPDATE / category_name / version
- DELETE / category_name / version

here we have:

  - category: gcontroller.examples.osl.etsi.org
  - version: 0.1.0


The project registers automatically the two Resource Specifications (RSpecs) and we added handler functions for create, update and delete.

So when you create CFSs services using these (RSpecs) the Service Orchestrator will call these functions via the queue

See BootstrapService that registers the two RSpecs as LogicalResourceSpecifications

The two classes has function to transform to TMF RSpecs, as well as to save back to OpenSlice their Resource Instances (thus they will go to the inventory)

The ResourceRepoService class has method calls for the handling of the messages from the orchestrator.

Useful metadata are included in some headers of the message:

- important header org.etsi.osl.resourceId  is the UUID of the related resource that was created in OpenSlice and reflects the resource that this controller will handle. NOTE: you need to UPDATE this resource back in TMFAPI and set the status at least ResourceStatusType.AVAILABLE or TERMINATED if there are errors, etc 
- header org.etsi.osl.serviceOrderId is the UUID of the related service order
- header org.etsi.osl.serviceId is the UUID of the related services 

