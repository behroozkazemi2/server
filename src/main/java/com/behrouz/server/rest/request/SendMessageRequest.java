package com.behrouz.server.rest.request;

/**
 * created by: Hapi
 **/


public class SendMessageRequest {


    private String mobile;

    private String text;

    private String text2;

    private String text3;

    private int refCode;

    private String template;


    public SendMessageRequest() {
    }


    public SendMessageRequest(String mobile, String text, int refCode) {
        this.mobile = mobile;
        this.text = text;
        this.refCode = refCode;
    }



    public SendMessageRequest(String mobile, String text, int refCode, String template) {
        this.mobile = mobile;
        this.text = text;
        this.refCode = refCode;
        this.template = template;

    }

    public SendMessageRequest(String mobile, String text, int refCode, Template template) {
        this.mobile = mobile;
        this.text = text;
        this.refCode = refCode;
        this.template = template.getKavenegarName();

    }

    public SendMessageRequest(String mobile, String text, String text2, String text3, Template template) {
        this.mobile = mobile;
        this.text = text;
        this.text2 = text2;
        this.text3 = text3;

        this.template = template.getKavenegarName();

    }



    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public String getText2() {
        return text2;
    }
    public void setText2(String text2) {
        this.text2 = text2;
    }



    public String getText3() {
        return text3;
    }
    public void setText3(String text3) {
        this.text3 = text3;
    }



    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public int getRefCode() {
        return refCode;
    }
    public void setRefCode(int refCode) {
        this.refCode = refCode;
    }


    public String getTemplate() {
        return template;
    }
    public void setTemplate(String template) {
        this.template = template;
    }


    public enum Template{

//        LINE_TAB("linetab") ,
//        MEHAN("mehan"),
        XIMA("XimaShop"),
        XIMA_CUSTOMER_BILL_ACCEPT("ximashopaccept"),
        XIMA_CUSTOMER_SPECIAL_CHANGED_STATUS("ximashopspacial"),
        XIMA_PROVIDER_SPECIAL_RECEIVED("ximashopprovidersp"),
        XIMA_PROVIDER_NEW_BILL("ximashopprovider"),
        XIMA_ACCOUNT_CHARGE("ximashopaccount");

        String kavenegarName;

        Template(String kavenegarName) {
            Template.this.kavenegarName = kavenegarName;
        }


        public String getKavenegarName(){
            return kavenegarName;
        }

        public static Template getByKavenegarName(String kavenegarName){
            for(Template temp : Template.values()){
                if(temp.kavenegarName.equals( kavenegarName )){
                    return temp;
                }
            }
            throw new RuntimeException( "bad kavenegar template name pass to SendMessageRequest.Template.getByName" );
        }

    }
}
