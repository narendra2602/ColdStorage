package com.coldstorage.util;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class OverWriteTableCellEditor extends javax.swing.AbstractCellEditor implements
TableCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField component = new JTextField();

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value==null)
		     value="";
		  if(value != null)
		    component.setText(value.toString());
		//component.selectAll();
		 if (isSelected) 
			 component.selectAll();
		
		//component.setSelectionStart(0);
		//component.setSelectionEnd(value.toString().length());
		return component;
	}

	@Override
	public Object getCellEditorValue() {
		return component.getText();
	}
}