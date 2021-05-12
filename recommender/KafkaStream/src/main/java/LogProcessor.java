import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

public class LogProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
    }

    @Override
    public void process(byte[] _, byte[] line) {
        // 把收集到的日志信息用string表示
        String input = new String(line);
        // 根据前缀"MOVIE_RATING_PREFIX:"从日志信息中提取评分数据
        if (input.contains("MOVIE_RATING_PREFIX:")) {
            System.out.println("提取到电影评分数据：" + input);
            input = input.split("MOVIE_RATING_PREFIX:")[1].trim();
            context.forward("logProcessor".getBytes(), input.getBytes());
        }
    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
