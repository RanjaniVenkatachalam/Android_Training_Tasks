package rv.ss4.recyclerview;

public class Data {

    private long id;
    private String androidVersions;
    private String version;

    public Data(long id,String androidVersions,String version){
        this.androidVersions=androidVersions;
        this.id=id;
        this.version=version;
    }

    public String getAndroidVersions() {
        return androidVersions;
    }

    public void setAndroidVersions(String androidVersions) {
        this.androidVersions = androidVersions;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
