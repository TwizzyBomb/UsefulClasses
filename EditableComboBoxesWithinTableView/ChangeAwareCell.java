package stackOverFlowExample;

import java.util.function.Function;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;

public class ChangeAwareCell<S,T> extends TableCell<S,T> {

    private Function<S, ObservableValue<T>> property ;
    private ObservableValue<T> lastObservableValue ;

    private ChangeListener<T> listener = (obs, oldValue, newValue) -> valueChanged(oldValue, newValue);

    public ChangeAwareCell(Function<S, ObservableValue<T>> property) {
        this.property = property ;
    }

    private void valueChanged(T oldValue, T newValue) {
        System.out.printf("Value changed from %s to %s %n", oldValue, newValue);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (lastObservableValue != null) {
            lastObservableValue.removeListener(listener);
        }
        if (empty) {
            setText(null);
        } else {
            lastObservableValue = property.apply((S) getTableRow().getItem());
            lastObservableValue.addListener(listener);
            setText(item.toString());
        }
    }
    
    
}