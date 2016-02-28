package io.jenkins.blueocean.service.embedded.rest;

import hudson.model.Project;
import hudson.model.TopLevelItem;
import io.jenkins.blueocean.commons.ServiceException;
import io.jenkins.blueocean.rest.model.BluePipeline;
import io.jenkins.blueocean.rest.model.BluePipelineContainer;
import jenkins.model.Jenkins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Vivek Pandey
 */
public class PipelineContainerImpl extends BluePipelineContainer {
    @Override
    public PipelineImpl get(String name) {
        TopLevelItem p = Jenkins.getActiveInstance().getItem(name);
        if (p instanceof Project) {
            return new PipelineImpl((Project)p);
        }

        // TODO: I'm going to turn this into a decorator annotation
        throw new ServiceException.NotFoundException(String.format("Pipeline %s not found", name));
    }

    @Override
    public Iterator<BluePipeline> iterator() {
        List<Project> projects = Jenkins.getActiveInstance().getAllItems(Project.class);
        List<BluePipeline> pipelines = new ArrayList<>();
        for (Project project : projects) {
            pipelines.add(new PipelineImpl(project));
        }
        return pipelines.iterator();
    }
}