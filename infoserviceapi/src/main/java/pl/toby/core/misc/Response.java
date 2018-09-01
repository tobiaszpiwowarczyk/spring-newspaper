package pl.toby.core.misc;

import org.springframework.http.HttpStatus;

public class Response<T> {

    private int status;
    private T content;

    public Response(HttpStatus status, T content) {
        this.status = status.value();
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
