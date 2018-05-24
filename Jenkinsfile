podTemplate(label: 'builder', 
  containers: [
    containerTemplate(image: 'docker:18.03.1-ce', name: 'docker', command: 'cat', ttyEnabled: true),
    containerTemplate(image: "mave:tag", name: 'mvn', command: 'cat', ttyEnabled: true)
    ],
  volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')])
  {
    node('builder') {
        stage('Build') {
          container('mvn') { 
            git 'https://github.com/gjyoung1974/Secure_Connection_Pool.git'
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
          }
        }
    } 
node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/gjyoung1974/Secure_Connection_Pool.git'
   }
   stage('Build') {
      // Run the maven build
      container('mvn') { 
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      }
   }

   stage('SonarQube analysis') { 
        withSonarQubeEnv('Sonar') { 
          sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.3.0.603:sonar ' + 
          '-f ./pom.xml ' +
          '-Dsonar.projectKey=:Secure_Connection_Pool:all:master ' +
          '-Dsonar.login=admin ' +
          '-Dsonar.password=admin ' +
          '-Dsonar.language=java ' +
          '-Dsonar.sources=./src/main/java'
        }
    }  

 stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
}
