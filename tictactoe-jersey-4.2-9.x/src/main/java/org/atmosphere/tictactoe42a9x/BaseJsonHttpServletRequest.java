
package org.atmosphere.tictactoe42a9x;

/**
 * Created by IntelliJ IDEA.
 * User: Jeny
 * Date: 03.04.12
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public class BaseJsonHttpServletRequest extends AbstractJsonHttpServletRequest {

    private String body;

//    @Override
    public String getBody() {
        return body;
    }

//    @Override
    public void setBody(String body) {
        this.body = body;
    }

}
