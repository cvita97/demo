package score.alarm.demo.mappers;

/**
 * Custom made mapper interface. It converts given object to another.
 * @param <O1> given object
 * @param <O2> object that will be created and mapped to
 */
public interface Mapper<O1, O2> {
    O2 mapTo(O1 o);
}
