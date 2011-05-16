package domain;

public interface ISelectable
{
	boolean contains (int x, int y); 
	void setSelected (boolean newVal); 
	boolean isSelected (); 
}
