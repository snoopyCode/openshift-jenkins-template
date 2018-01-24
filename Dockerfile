# Source: https://github.com/openshift/jenkins/blob/master/2/Dockerfile.rhel7
FROM registry.access.redhat.com/openshift3/jenkins-2-rhel7

# Copy config file templates
COPY resources/* /usr/tmp/

# Copy post-init scripts
RUN mkdir /var/lib/jenkins/init.groovy.d
COPY src/* /var/lib/jenkins/init.groovy.d/


