package unjenkins.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class View {

    private Object description;
    private List<Job> jobs = new ArrayList<Job>();
    private String name;
    private List<Object> property = new ArrayList<Object>();
    private String url;

    /**
     *
     * @return
     * The description
     */
    public Object getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(Object description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The jobs
     */
    public List<Job> getJobs() {
        return jobs;
    }

    /**
     *
     * @param jobs
     * The jobs
     */
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The property
     */
    public List<Object> getProperty() {
        return property;
    }

    /**
     *
     * @param property
     * The property
     */
    public void setProperty(List<Object> property) {
        this.property = property;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}