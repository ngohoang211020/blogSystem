pipeline {
  agent {
    label 'hoang-ubuntu'
  }
  environment {
    APP_USER = "blogsystem"
    APP_NAME = "blog-system"
    APP_TYPE = "jar"
    TAG_NAME = "1.0"
    IMAGE_TAG = "${APP_NAME}:${TAG_NAME}"
    PROCESS_NAME = "${APP_NAME}.${APP_TYPE}"
    PATH_PROJECT = "/datas/${APP_USER}"
    BUILD_MAVEN_SCRIPT = "mvn clean install -DskipTests=true"
    DOCKER_HUB = "211020"
    DOCKERHUB_CREDENTIALS = credentials('docker-credentials')
  }
  stages {
    stage('build maven') {
      steps {
        sh(script: """ ${BUILD_MAVEN_SCRIPT}  """, label: "Build with maven")
      }
    }
    stage('build and push image') {
      steps {
        sh(script: """ sudo cp . $PATH_PROJECT """, label: "Copy .jar file into deploy folder")
        sh "  docker build $PATH_PROJECT -t ${IMAGE_TAG} \
              && docker tag ${IMAGE_TAG} ${DOCKER_HUB}/${IMAGE_TAG} \
              && echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin \
              && docker push ${DOCKER_HUB}/${IMAGE_TAG} \
              && docker rmi ${DOCKER_HUB}/${IMAGE_TAG} \
              && docker pull ${DOCKER_HUB}/${IMAGE_TAG} \
              && docker-compose up -d"
      }
    }
    stage('check log') {
      steps {
        sh " docker ps -a"
      }
    }
  }
}
