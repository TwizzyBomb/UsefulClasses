package stackOverFlowExample;
import java.util.ArrayList;
import javafx.beans.property.*;


public class FieldMappingRow {

	public ArrayList<FieldMappingRow> selectedList = new ArrayList<FieldMappingRow>();
	private BooleanProperty selected;
	private SimpleStringProperty source;
	private SimpleStringProperty target;
	private SimpleStringProperty compareType;
	private SimpleStringProperty operator;
	private SimpleStringProperty errors;
	private SimpleStringProperty status;
	private SimpleStringProperty startTime;
	
	@SuppressWarnings("restriction")
	public FieldMappingRow(Boolean selected, String source,
			String target, String compareType, String operator,
			String errors, String status, String startTime) {
		this.selected = new SimpleBooleanProperty(selected);
		this.source = new SimpleStringProperty(source);
		this.target = new SimpleStringProperty(target);
		this.compareType = new SimpleStringProperty(compareType);
		this.operator = new SimpleStringProperty(operator);
		this.errors = new SimpleStringProperty(errors);
		this.status = new SimpleStringProperty(status);
		this.startTime = new SimpleStringProperty(startTime);
	}
	
	/*Empty Constructor to use selectedList functions*/
	public FieldMappingRow()
	{
		
	}

	public void addToSelected(FieldMappingRow row)
	{
		selectedList.add(row);
	}
	public void removeFromSelected(FieldMappingRow row)
	{
		selectedList.remove(row);
	}
	public ArrayList<FieldMappingRow> getSelectedList()
	{
		return selectedList;
	}
	public BooleanProperty getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected.set(selected);
	}

	public String getSource() {
		return source.get();
	}

	public void setSource(String source) {
		this.source.set(source);
	}
	
	public StringProperty sourceProperty() {
		return source;
	}
	
	public void setSourceProperty(StringProperty source) {
		this.source = (SimpleStringProperty) source;
	}

	public String getTarget() {
		return target.get();
	}

	public void setTarget(String target) {
		this.target.set(target);
	}
	
	public StringProperty targetProperty() {
		return target;
	}

	public String getCompareType() {
		return compareType.get();
	}

	public void setCompareType(String compareType) {
		this.compareType.set(compareType);
	}

	public String getOperator() {
		return operator.get();
	}

	public void setOperator(String operator) {
		this.operator.set(operator);
	}

	public String getErrors() {
		return errors.get();
	}

	public void setErrors(String errors) {
		this.errors.set(errors);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public String getStartTime() {
		return startTime.get();
	}

	public void setStartTime(String startTime) {
		this.startTime.set(startTime);
	}
	
	
}
