package trade.spring.data.neo4j.apiModel;

/**
 * Created by Wayne.A.Z on 2019/1/18.
 */
public class GeneralResponse<T> {
    private int status;
    private T obj;
    private String errorMes;

    public GeneralResponse() {
        errorMes = "";
        status = 1;
    }

    public GeneralResponse(String err) {
        errorMes = err;
        status = 2;
    }

    public GeneralResponse(T obj) {
        this.obj = obj;
        errorMes = "";
        status = 1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getErrorMes() {
        return errorMes;
    }

    public void setErrorMes(String errorMes) {
        this.status = 2;
        this.errorMes = errorMes;
    }

    public boolean isSuccess() {
        return status == 1;
    }
}
