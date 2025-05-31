package api.constants;

public enum ApiEndpoints {

    LOGIN("/users/login"),
    CREATE_NEW_NOTE("/notes");

    private final String path;

    ApiEndpoints(String path){
        this.path = path;
    }

    public String getPath(Object... args){
        return String.format(path,args);
    }
}


