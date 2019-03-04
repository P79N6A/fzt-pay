package cn.yesway.pay.order.exception.handle;


/**
 * 异常处理器基类<br>
 * 通过继承该处理器，实现自己的process方法，传入应有的返回值类型T
 * 
 * @author nzc
 */
public abstract class ExceptionHandler<T> {

    /**
     * 下一个处理环节
     */
    private ExceptionHandler<T> nextnode;

    protected ExceptionHandler() {

    }

    /**
     * 异常处理抽象方法
     * 
     * @param e
     *            要处理的异常
     * @return 返回的应答
     */
    public abstract T process(Exception e);

    /**
     * 获取下一个异常处理器
     * 
     * @return
     */
    public ExceptionHandler<T> getNextnode() {
        return nextnode;
    }

    /**
     * 设置当前异常处理器的下一环节处理器
     * 
     * @param nextnode
     *            下一环节的异常处理器
     * @return 返回nextnode<br>
     *         以可以连续setNextnode<br>
     *         例如: handeler.setNextnode(nextnode1).setNextnode(nextnode2)...;
     */
    public ExceptionHandler<T> setNextnode(ExceptionHandler<T> nextnode) {
        this.nextnode = nextnode;
        return nextnode;
    }

}
