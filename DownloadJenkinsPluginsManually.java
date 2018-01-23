package com.bmw.ppsea;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DownloadJenkinsPluginsManually {

    @Test
    public void execute() throws IOException {
        String jenkinsDownloadUrl = "http://mirrors.jenkins-ci.org/plugins/";
        List<String> plugins = Files.lines(Paths.get("C:\\git\\jenkins-template\\plugins.txt")).collect(Collectors.toList());
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
