package com.bmw.ppsea;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DownloadJenkinsPluginsManually {

    @Test
    public void execute() {
        String jenkinsDownloadUrl = "http://updates.jenkins-ci.org/download/plugins/";
        String[] plugins = {
                "ace-editor:1.1",
                "branch-api:2.0.9",
                "cloudbees-folder:6.0.4",
                "credentials:2.1.13",
                "display-url-api:2.0",
                "git-client:2.7.0",
                "git:3.3.0",
                "git-server:1.7",
                "handlebars:1.1.1",
                "icon-shim:2.0.3",
                "jquery-detached:1.2.1",
                "junit:1.20",
                "mailer:1.20",
                "mapdb-api:1.0.9.0",
                "matrix-auth:1.5",
                "matrix-project:1.10",
                "mercurial:1.59",
                "momentjs:1.1.1",
                "multiple-scms:0.6",
                "openshift-login:1.0.0",
                "openshift-sync:0.1.32",
                "pipeline-build-step:2.6",
                "pipeline-graph-analysis:1.3",
                "pipeline-stage-step:2.2",
                "pipeline-stage-view:2.6",
                "pipeline-utility-steps:1.3.0",
                "plain-credentials:1.4",
                "script-security:1.40",
                "ssh-credentials:1.13",
                "subversion:2.7.2",
                "workflow-aggregator:2.1",
                "workflow-basic-steps:2.4",
                "workflow-cps-global-lib:2.8",
                "workflow-durable-task-step:2.11",
                "workflow-multibranch:2.14",
                "workflow-remote-loader:1.4",
                "workflow-scm-step:2.4",
                "docker-commons:1.11",
                "workflow-support:2.14",
                "scm-api:2.2.6",
                "jsch:0.1.54.1",
                "bouncycastle-api:2.16.2",
                "structs:1.10",
                "token-macro:2.3",
                "config-file-provider:2.17",
                "javadoc:1.4",
                "nodejs:1.2.4",
                "apache-httpcomponents-client-4-api:4.5.3-2.0",
                "windows-slaves:1.3.1",
                "antisamy-markup-formatter:1.5",
                "maven-plugin:3.0",
                "authentication-tokens:1.3",
                "workflow-step-api:2.14",
                "credentials-binding:1.14",
                "docker-java-api:3.0.14",
                "ssh-slaves:1.25",
                "jackson2-api:2.7.3",
                "docker-plugin:1.0.4",
                "variant:1.1",
                "durable-task:1.17",
                "kubernetes:1.1.3",
                "openshift-client:1.0.2",
                "workflow-api:2.24",
                "openshift-pipeline:1.0.52",
                "pipeline-rest-api:2.9",
                "pipeline-model-api:1.2.6",
                "workflow-cps:2.41",
                "workflow-job:2.11.2",
                "pipeline-model-declarative-agent:1.1.1",
                "pipeline-model-extensions:1.2.6",
                "pipeline-npm:0.9.1",
                "docker-workflow:1.14",
                "pipeline-milestone-step:1.3.1",
                "pipeline-stage-tags-metadata:1.2.6",
                "pipeline-input-step:2.8",
                "pipeline-model-definition:1.2.6"
        };
        for (String plugin : plugins) {
            try {
                String[] tokens = plugin.split(":");
                URL url = new URL(jenkinsDownloadUrl + tokens[0] + "/" + tokens[1] + "/" + tokens[0] + ".hpi");
                FileUtils.copyURLToFile(url, new File("C:\\git\\jenkins-template\\plugins\\" + tokens[0] + ".hpi"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}