# Slicing Generic Controller

OpenSlice's service orchestrator (OSOM) delegates the provisioning of resources of a given category to external controllers. This controller handles the `ResourceSlice` and `ResourceUE` resources (category `com.itav.osl.gcontroller`, version `0.0.1`) by calling the 5GAIner Network Slice Manager API.

It derives from the ETSI OpenSlice generic controller example at: https://labs.etsi.org/rep/osl/code/addons/org.etsi.osl.examples.gcontroller
