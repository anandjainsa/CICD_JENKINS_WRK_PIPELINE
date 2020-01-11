def call()
{
    sh """
      groupId=$(mvn -q -Dexec.executable=echo -Dexec.args=${project.groupId}  --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
       """
}

