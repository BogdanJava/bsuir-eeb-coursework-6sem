package by.bsuir.eeb.rsoicoursework.model.dto;

import lombok.Getter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 19.03.2018 / 23:13
 * email: bogdanshishkin1998@gmail.com
 */

@Getter
public class Page {
    private int from;
    private int length;

    private Page(int from, int length) {
        this.from = from;
        this.length = length;
    }

    public static Page getInstance(int from, int length) {
        Assert.isTrue(from >= 0, "\"from\" should be more than 0");
        Assert.isTrue(length > 0 && length < 50,
                "\"length\" should be more than 0 and less than 50");

        return new Page(from, length);
    }

    public static Page fromRequest(HttpServletRequest request) {
        String fromStr = request.getParameter("from");
        String lengthStr = request.getParameter("length");

        Integer from;
        Integer length;
        if (fromStr != null && lengthStr != null) {
            from = Integer.parseInt(fromStr);
            length = Integer.parseInt(lengthStr);
        } else {
            from = 0;
            length = 10;
        }

        return getInstance(from, length);
    }


}
