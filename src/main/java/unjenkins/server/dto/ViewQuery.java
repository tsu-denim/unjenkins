package unjenkins.server.dto;

import java.util.Date;

/**
 * Created by Kurt on 12/1/15.
 */
public class ViewQuery {
    private String name = null;
    private String folder = null;
    private String filter = null;


    public ViewQuery(){

    };

    public ViewQuery(String name, String filter){
        this.name = name;
        this.filter = filter;
    }

    public ViewQuery(String name, String folder, String filter){
        this.name = name;
        this.folder = folder;
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public boolean isSubView(){
        if (folder == null){
            return false;
        }

        return true;
    }
}
