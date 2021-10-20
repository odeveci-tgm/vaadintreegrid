package org.vaadin.example;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public TreeGrid<Department> grid = new TreeGrid<>();

    public MainView() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.getStyle().set("border", "1px solid #9E9E9E");
        Button button1 = new Button("Button1");
        Button button2 = new Button("Button2");
        button2.setIconAfterText(true);
        button2.setIcon(VaadinIcon.OPEN_BOOK.create());
        button2.getElement().getStyle().set("margin-left", "auto");
        button2.addClickListener( buttonClickEvent -> new DialogFenster(this).open());
        layout.add(button1,button2);
        DepartmentData departmentData = new DepartmentData();
        grid.setItems(departmentData.getRootDepartments(),
                departmentData::getChildDepartments);
        grid.addHierarchyColumn(Department::getName)
                .setHeader("Department Name");
        grid.addColumn(Department::getManager).setHeader("Manager");
        add(layout,grid);
    }


}