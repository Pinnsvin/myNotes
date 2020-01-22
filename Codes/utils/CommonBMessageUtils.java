import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Pinnsvin
 * @description: 业务性错误信息工具类
 */
public class CommonBMessageUtils {

    /** 过滤列表 */
    private static List<CommonBMessage> filterList = new ArrayList<CommonBMessage>(0);
    private static List<CommonBMessage> whiteList = new ArrayList<CommonBMessage>(0);

    private static int minLevel;

    public final static String CODE = "code";
    public final static String MESSAGE = "message";
    public final static String LEVEL = "level";
    public final static CommonBMessage defaultValue = new CommonBMessage("A0000", "我是默认信息", 10);
    public final static CommonBMessage defaultTimeOutValue = new CommonBMessage("099", "网络异常，请稍后再试", 10);

    private static CommonBMessageUtils commonBMessageUtils;
    /** 是否转码，默认关闭 使用的时候开启 */
    private boolean transcoding;

    private CommonBMessageUtils() {
    }

    public static CommonBMessageUtils getInstance() {
        if (commonBMessageUtils == null) {
            commonBMessageUtils = new CommonBMessageUtils();
        }
        // 获取单例 关闭转码 使用的时候手动开启
        commonBMessageUtils.setTranscoding(false);
        return commonBMessageUtils;
    }

    public void setTranscoding(boolean transcoding) {
        this.transcoding = transcoding;
    }

    static {
        // whiteList.add(new CommonBMessage("A3099", "连接超时，请稍后重试", 9));
        // whiteList.add(new CommonBMessage("A4099", "连接超时，请稍后重试", 9));
        // whiteList.add(new CommonBMessage("A1099", "连接超时，请稍后重试", 9));
        // whiteList.add(new CommonBMessage("A2099", "连接超时，请稍后重试", 9));
        whiteList.add(new CommonBMessage("A001", "业务信息错误1", 9));
        whiteList.add(new CommonBMessage("A002", "业务信息错误2", 9));
        whiteList.add(defaultTimeOutValue);
        whiteList.add(defaultValue);
    }

    public void config(List<CommonBMessage> filterList, int minLevel) {
        configFilterList(filterList);
        configMinLevel(minLevel);
    }

    /**
     * 设置最小级别
     * 
     * @param minLevel
     */
    public void configMinLevel(int minLevel) {
        CommonBMessageUtils.minLevel = minLevel;
    }

    /**
     * 配置过滤列表
     */
    public void configFilterList(List<CommonBMessage> filterList) {
        CommonBMessageUtils.filterList = filterList;
    }

    /**
     * 配置白名单列表
     */
    public void configWhiteList(List<CommonBMessage> whiteList) {
        CommonBMessageUtils.whiteList = whiteList;
    }

    /**
     * 添加错误信息
     */
    public boolean add(List<CommonBMessage> bMessages, CommonBMessage message) {
        if (bMessages.stream()
                .allMatch(item -> message.getCode() != null && !message.getCode().equals(item.getCode()))) {
            return bMessages.add(message);
        }
        return false;
    }

    /**
     * 获取所有错误信息（过滤后）
     * 
     * @param messages
     * @return
     */
    public List<CommonBMessage> getAllMess(List<CommonBMessage> messages) {
        if (transcoding) {
            return filter(transcoding(messages));
        }
        return filter(messages);
    }

    /**
     * 获取所有错误信息（匹配白名单）
     * 
     * @param messages
     * @return
     */
    public List<CommonBMessage> getAllMessWhiteList(List<CommonBMessage> messages) {
        if (transcoding) {
            return filterWhiteList(transcoding(messages));
        }
        return filterWhiteList(messages);
    }

    /**
     * 获取唯一一个错误信息（获取级别最高的）
     * 
     * @param messages
     * @return
     */
    public CommonBMessage getUniqueMess(List<CommonBMessage> messages) {
        List<CommonBMessage> bMessages = new ArrayList<>(0);
        if (transcoding) {
            bMessages = transcoding(messages);
        }
        bMessages = filter(bMessages);
        Optional<CommonBMessage> max = bMessages.stream().max(Comparator.comparingInt(item -> item.getLevel()));
        if (max.isPresent()) {
            return max.get();
        }
        return defaultValue;
    }

    /**
     * 获取白名单中唯一一个错误信息（获取级别最高的）
     * 
     * @param messages
     * @return
     */
    public CommonBMessage getUniqueMessWhiteList(List<CommonBMessage> messages) {
        List<CommonBMessage> bMessages = new ArrayList<>(0);
        if (transcoding) {
            bMessages = transcoding(messages);
        }
        bMessages = filterWhiteList(bMessages);
        Optional<CommonBMessage> max = bMessages.stream().max(Comparator.comparingInt(item -> item.getLevel()));
        if (max.isPresent()) {
            return max.get();
        }
        return defaultValue;
    }

    /**
     * 过滤（匹配白名单）
     * 
     * @param messages
     * @return
     */
    private List<CommonBMessage> filterWhiteList(List<CommonBMessage> messages) {
        return messages.stream().filter(item -> {
            if (CommonBMessageUtils.whiteList != null && !CommonBMessageUtils.whiteList.isEmpty()) {
                return CommonBMessageUtils.whiteList.stream().anyMatch(white -> {
                    try {
                        if (white.getCode().equals(item.getCode()) && white.getMessage().equals(item.getMessage())
                                && white.getLevel() == item.getLevel()) {
                            return true;
                        }
                        return false;
                    } catch (NullPointerException e) {
                        throw new NullPointerException("CommonBMessage白名单列表中编码和信息不能为空！");
                    }
                });
            } else {
                return true;
            }

        }).collect(Collectors.toList());
    }

    /**
     * 过滤（过滤黑名单）
     * 
     * @param messages
     * @return
     */
    private List<CommonBMessage> filter(List<CommonBMessage> messages) {
        return messages.stream().filter(item -> {
            if (CommonBMessageUtils.filterList != null && !CommonBMessageUtils.filterList.isEmpty()) {
                return CommonBMessageUtils.filterList.stream().noneMatch(black -> {
                    try {
                        if (black.getCode().equals(item.getCode()) && black.getMessage().equals(item.getMessage())
                                && black.getLevel() == item.getLevel()) {
                            return true;
                        }
                        return false;
                    } catch (NullPointerException e) {
                        throw new NullPointerException("CommonBMessage过滤列表中编码和信息不能为空！");
                    }
                });
            } else {
                return true;
            }

        }).collect(Collectors.toList());
    }

    /**
     * 过滤
     * 
     * @param type     取值（code-根据code匹配,message-根据message匹配,level-根据级别匹配）
     * @param values   过滤列表
     * @param messages
     * @return
     */
    public List<CommonBMessage> filter(String type, List<String> values, List<CommonBMessage> messages) {
        switch (type) {
        case CODE:
            return messages.stream().filter(item -> {
                if (values != null && !values.isEmpty()) {
                    return values.stream().noneMatch(filter -> {
                        try {
                            return filter.equals(item.getCode());
                        } catch (NullPointerException e) {
                            throw new NullPointerException("过滤列表内容不能包含null");
                        }
                    });
                } else {
                    return true;
                }
            }).collect(Collectors.toList());
        case MESSAGE:
            return messages.stream().filter(item -> {
                if (values != null && !values.isEmpty()) {
                    return values.stream().noneMatch(filter -> {
                        try {
                            return filter.equals(item.getMessage());
                        } catch (NullPointerException e) {
                            throw new NullPointerException("过滤列表内容不能包含null");
                        }
                    });
                } else {
                    return true;
                }
            }).collect(Collectors.toList());
        case LEVEL:
            return messages.stream().filter(item -> {
                if (values != null && !values.isEmpty()) {
                    return values.stream().noneMatch(filter -> {
                        try {
                            return Integer.valueOf(filter) == item.getLevel();
                        } catch (NullPointerException e) {
                            throw new NullPointerException("过滤列表内容不能包含null");
                        } catch (NumberFormatException e) {
                            throw new NumberFormatException("过滤级别应为正确的数字");
                        }
                    });
                } else {
                    return true;
                }
            }).collect(Collectors.toList());
        default:
            return filter(messages);
        }

    }

    /**
     * 转码
     * 
     * @param messages
     * @return
     */
    private List<CommonBMessage> transcoding(List<CommonBMessage> messages) {
        List<CommonBMessage> commonBMessages = new ArrayList<>(0);
        messages.stream().forEach(item -> {
            if (item.getCode() != null && item.getCode().endsWith("099")) {
                item.setCode(defaultTimeOutValue.getCode());
                item.setMessage(defaultTimeOutValue.getMessage());
                item.setLevel(defaultTimeOutValue.getLevel());
            }
        });
        return messages;
    }

    public static void main(String[] args) {
        filterList.add(new CommonBMessage("001", "test", 1));
        filterList.add(new CommonBMessage("002", "test2", 2));
        filterList.add(new CommonBMessage("004", "test4", 1));
        filterList.add(new CommonBMessage("008", "test8", 8));
        filterList.add(new CommonBMessage("009", "test8", 9));

        whiteList.add(new CommonBMessage("099", "test9", 9));
        whiteList.add(new CommonBMessage("0099", "test6", 6));
        whiteList.add(new CommonBMessage("099", "网络异常，请稍后再试", 10));

        List<CommonBMessage> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            messages.add(new CommonBMessage("09" + i, "test" + i, i));
        }
        messages.add(new CommonBMessage("2099", "hh", 9));

        CommonBMessageUtils utils = CommonBMessageUtils.getInstance();
        List<String> filters = new ArrayList<>();
        filters.add("test3");
        System.out.println("按信息过滤" + utils.filter(CommonBMessageUtils.MESSAGE, filters, messages));
        List<String> filters2 = new ArrayList<>();
        filters2.add("004");
        System.out.println("按编码过滤" + utils.filter(CommonBMessageUtils.CODE, filters2, messages));
        List<String> filters3 = new ArrayList<>();
        filters3.add("7");
        System.out.println("按级别过滤" + utils.filter(CommonBMessageUtils.LEVEL, filters3, messages));
        System.out.println("唯一" + utils.getUniqueMess(messages));
        System.out.println("所有" + utils.getAllMess(messages));
        System.out.println("匹配白名单(所有)" + utils.getAllMessWhiteList(messages));
        System.out.println("匹配白名单(唯一)" + utils.getUniqueMessWhiteList(messages));

        utils.setTranscoding(true);// 开启转码
        System.out.println("开启转码-匹配白名单(所有)" + utils.getAllMessWhiteList(messages));
        System.out.println("开启转码-匹配白名单(唯一)" + utils.getUniqueMessWhiteList(messages));
    }
}
