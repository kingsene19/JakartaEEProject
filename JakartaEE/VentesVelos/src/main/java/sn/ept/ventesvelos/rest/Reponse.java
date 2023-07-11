package sn.ept.ventesvelos.rest;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reponse")
public class Reponse {
    private String msg;

    public Reponse() {}

    public Reponse(String msg) {this.msg = msg;}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
