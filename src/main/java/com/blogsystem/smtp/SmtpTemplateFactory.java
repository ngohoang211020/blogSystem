package com.blogsystem.smtp;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SmtpTemplateFactory {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final VelocityEngine velocityEngine;

    public SmtpTemplateFactory() {
        this.velocityEngine = this.getDefaultEngine();
    }

    public SmtpTemplateFactory(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public Template getTemplate(SmtpTemplate template) {
        return velocityEngine.getTemplate(String.format("templates/%s.vm", template.getFilename()), DEFAULT_CHARSET.name());
    }

    private VelocityEngine getDefaultEngine() {
        var engine = new VelocityEngine();

        engine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "class");
        engine.setProperty("resource.loader.class.class", ClasspathResourceLoader.class.getName());
        engine.init();

        return engine;
    }
}
