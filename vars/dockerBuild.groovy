def call()
     {
          sh '''
             groupId=`mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.groupId -q -DforceStdout`
             groupId=`echo $groupId| tr . /`
             artifactId=`mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.artifactId -q -DforceStdout`
             version=`mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate -Dexpression=project.version -q -DforceStdout`
             
                     echo "groupId is: $groupId artifactId is: $artifactId version is: $version"
                     echo "version=$version" >> env.properties
             
             imagename="${artifactId}-$version-${BUILD_ID}"
                dtr="npdtr.ttgtpmg.net"
                mv ./target/$artifactId.jar ./target/services.jar
                docker build -t $dtr/org-middleware/${artifactId}-snapshot:$imagename .
                docker push $dtr/org-middleware/${artifactId}-snapshot:$imagename
                docker tag $dtr/org-middleware/${artifactId}-snapshot:$imagename $dtr/org-middleware/${artifactId}-snapshot:latest
                docker push $dtr/org-middleware/${artifactId}-snapshot:latest
		sed -i.bak "s#{{ image.tag }}#$dtr/org-middleware/${artifactId}-snapshot:latest#" ./kube/*/*.yaml
                  '''
 }
