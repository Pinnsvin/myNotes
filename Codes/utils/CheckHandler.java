import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * @description:
 */
public class CheckHandler {
    static final long serialVersionUID = -3387516993124229948L;
    private final static Logger logger = LoggerFactory.getLogger(CheckHandler.class);

    private boolean existMsg;
    private boolean isSort;
    private Integer sortId;
    private int count;
    private String split;
    private String title;
    private List<String> messages;
    private TreeMap<Integer, String> sortMessages;

    public boolean isExistMsg() {
        return existMsg;
    }

    public int getCount() {
        return count;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CheckHandler() {
        this(false);
    }

    public CheckHandler(boolean isSort) {
        this(isSort, "");
    }

    public CheckHandler(boolean isSort, String split) {
        this(isSort, null, split);
    }

    public CheckHandler(boolean isSort, String title, String split) {
        existMsg = false;
        count = 0;
        this.split = split;
        this.title = title;
        if (isSort) {
            this.isSort = isSort;
            sortId = 0;
            sortMessages = new TreeMap<Integer, String>();
        } else {
            messages = new ArrayList<String>(0);
        }
    }

    public void add(String message, String split) {
        if (message != null && message.length() > 0 && message.trim().length() > 0) {
            existMsg = true;
            count++;
            if (isSort) {
                sortId++;
                sortMessages.put(sortId, message + split);
            } else {
                messages.add(message + split);
            }
        }
    }

    public void add(String message) {
        if (message != null && message.length() > 0 && message.trim().length() > 0) {
            existMsg = true;
            count++;
            if (isSort) {
                sortId++;
                sortMessages.put(sortId, message + split);
            } else {
                messages.add(message + split);
            }
        }
    }

    public void add(Exception e, int length) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        String msg = "";
        try {
            e.printStackTrace(pw);
            msg = sw.toString();
            if (length > 0) {
                msg = sw.toString() != null && sw.toString().length() > length ? sw.toString().substring(0, length)
                        : sw.toString();
            }
        } finally {
            try {
                sw.close();
                pw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        add(msg);
    }

    public void remove(String message) {
        if (message != null && message.length() > 0 && message.trim().length() > 0) {
            if (isSort) {
                for (Integer key : sortMessages.keySet()) {
                    String value = sortMessages.get(key);
                    if (message.equals(value)) {
                        sortMessages.remove(key);
                        count--;
                    }
                }
                existMsg = sortMessages.size() > 0;
            } else {
                for (String string : messages) {
                    if (message.equals(string)) {
                        messages.remove(string);
                        count--;
                    }
                }
                existMsg = messages.size() > 0;
            }
        }
    }

    public void remove(Integer sortNo) throws Exception {
        if (sortNo != null && sortNo.intValue() > 0) {
            if (isSort) {
                sortMessages.remove(sortNo);
                count--;
                existMsg = sortMessages.size() > 0;
            } else {
                throw new UnsupportedOperationException("无序检验列表不能按序号移除记录！");
            }
        }
    }

    public String getStringMessage() {
        String message = "";
        if (existMsg) {
            if (isSort) {
                Integer integ = null;
                Iterator iterator = sortMessages.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry next = (Map.Entry) iterator.next();
                    Integer key = (Integer) next.getKey();
                    String value = (String) next.getValue();
                    message += key + "-" + value;
                }
            } else {
                // message = messages.toString();
                for (String msg : messages) {
                    message += msg;
                }
            }
            message = message.endsWith(split) ? message.substring(0, message.lastIndexOf(split)) : message;
        }
        return message;
    }

    public String getStringMessageWithTitle() {
        String message = "";
        if (existMsg) {
            message = getStringMessage();
            message = title != null ? title + message : message;
        }
        return message;
    }

    public String getStringMessage(int length) {
        String message = getStringMessage();
        message = message.length() > length ? message.substring(0, length) : message;
        return message;
    }

    public void clearAll() {
        if (isSort) {
            sortMessages = new TreeMap<Integer, String>();
        } else {
            messages = new ArrayList<String>(0);
        }
        existMsg = false;
    }

    public void print() {
        String message = getStringMessage();
        if (message.equals("")) {
            System.err.println(getClass().getName() + ": 校验提示信息为空。");
        } else {
            System.out.println(getClass().getName() + ":" + message);
        }
    }

    public void printLog() {
        String message = getStringMessage();
        if (message.equals("")) {
            logger.info(getClass().getName() + ": 校验提示信息为空。");
        } else {
            logger.info(getClass().getName() + ":" + message);
        }
    }

    public static void main(String[] args) throws Exception {
        // CheckHandler check = new CheckHandler(true, " | ");
        // CheckHandler check = new CheckHandler();
        CheckHandler check = new CheckHandler(true, "测试校验信息：", ",");
        check.add("用户名不存在！");
        check.add("密码错误！");
        check.add(null);
        check.add("日期格式有误");
        String str = null;
        Integer inter = new Integer(1);
        // check.remove(inter);
        // check.remove(2);
        check.remove(str);
        check.printLog();
        System.out.println(check.getCount());
        System.out.println(check.isExistMsg());
        System.out.println(check.getStringMessage());
        System.out.println(check.getStringMessage(10));

        CheckHandler checkHandler = new CheckHandler();
        checkHandler.add("");
        System.out.println(checkHandler.isExistMsg());
    }
}
