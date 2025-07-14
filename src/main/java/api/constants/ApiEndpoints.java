package api.constants;

public enum ApiEndpoints {

    LOGIN("/users/login"),
    CREATE_NEW_NOTE("/notes"),
    GET_ALL_NOTES("/notes"),
    GET_SINGLE_NOTE("/notes/%s");

    private final String path;

    ApiEndpoints(String path){
        this.path = path;
    }

    public String getPath(Object... args){
        return String.format(path,args);
    }
}


