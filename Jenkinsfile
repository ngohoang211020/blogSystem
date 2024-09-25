pipeline {
    agent {
        label 'hoang-ubuntu'
    }
    environment {
        appUser = "blogsystem"
        appName = "blog-system"
        appType = "jar"
        processName = "${appName}.${appType}"
        folderDeploy = "/datas/${appUser}"
        buildScript = "mvn clean install -DskipTests=true"
        copyScript = "sudo cp target/${processName} ${folderDeploy}"
        permissionScript = "sudo chown -R ${appUser}. ${folderDeploy}"
        killScript = "sudo kill -9 \$(ps -ef| grep ${processName} | grep -v grep | awk '{print \$2}')"
        runScript = 'sudo su ${appUser} -c "cd ${folderDeploy}; cloudinary.key=117784362653246 cloudinary.secret-key=XfcUsfz7LK7cdjj7Q5rf6BrxCEo java -jar ${processName} > nohup.out 2>&1 &"'
    }
    
    stages {
        stage('build') {
            steps {
                sh(script: """ ${buildScript}  """, label: "Build with maven")
            }
        }
        stage('deploy') {
            steps {
                sh(script: """ ${copyScript}  """, label: "Copy .jar file into deploy folder")
                sh(script: """ ${permissionScript}  """, label: "Grant permission")
                sh(script: """ ${killScript}  """, label: "Terminate the running process")
                sh(script: """ ${runScript}  """, label: "Run project")
            }
        }
    }
}
