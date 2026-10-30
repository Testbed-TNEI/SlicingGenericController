### Generate openapi spec

```bash
openapi-generator-cli generate -i openapi_migrate_to.yaml -g spring -o spring-client --additional-properties=useSpringBoot3="true",groupId="com.itav.slicemanager",artifactId="api-models",artifactVersion="0.1.0",modelPackage="com.itav.slicemanager.models",apiPackage="com.itav.slicemanager.api"
```

### Publish Maven Package

```bash
./publish-spring-client-local-maven.sh
```
