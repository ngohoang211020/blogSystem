pipeline {
  agent {
    label 'hoang-ubuntu'
  }
  environment {
    APP_USER = "blogsystem"
    APP_NAME = "blog-system"
    APP_TYPE = "jar"
    TAG_NAME = "1.0"
    DOCKER_HUB = "211020"
    IMAGE_TAG = "${DOCKER_HUB}/${APP_NAME}:${TAG_NAME}"
    BUILD_MAVEN_SCRIPT = "mvn clean install -DskipTests=true"
    PATH_PROJECT = "/datas/${APP_USER}"
    DOCKERHUB_CREDENTIALS = credentials('docker-credentials')
  }
  stages {
    stage('Build Maven') {
      steps {
        sh "  mvn clean install -DskipTests=true \
              && sudo cp -R . $PATH_PROJECT \
              && sudo chown -R blogsystem. $PATH_PROJECT"
      }
    }
    stage('build and push image') {
      steps {
        sh "su ${APP_USER} -c 'docker build $PATH_PROJECT -f Dockerfile -t ${IMAGE_TAG} \
               && echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin \
               && docker push ${IMAGE_TAG} \
               && docker rmi  ${IMAGE_TAG} \
               && docker pull ${IMAGE_TAG} \
               && docker-compose up -d'"
      }
    }
    stage('check log') {
      steps {
        sh "docker ps -a"
      }
    }
  }
}
