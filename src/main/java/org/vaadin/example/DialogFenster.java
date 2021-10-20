package org.vaadin.example;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class DialogFenster extends Dialog {

    private static final TextField name = new TextField("Name");
    private static final TextField manager = new TextField("Manager");
    private static final TextField id = new TextField("id");
    private static final TextField parent = new TextField("parent");
    private static final TextField test = new TextField("Test");
    private static final Button save = new Button("Speichern");
    private static final Button visible = new Button("SetVisible");
    private static final Binder<Department>  binder = new Binder<>(Department.class);
    private static final RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
    private final MainView mainView;

    private final Department d = new Department(1,"Ousmane", null, "Osmane");

    public DialogFenster(MainView mainView) {
        this.mainView = mainView;
        FormLayout fl = createLayout();
        fl.setVisible(false);
        radioButtonGroup.setLabel("Wollen Sie bestehende Gruppe bearbeiten?");
        radioButtonGroup.setItems("Ja", "Nein");
        binder.bind(name, Department::getName,
                Department::setName);
        binder.bind(manager, Department::getManager,
                Department::setManager);
        binder.forField(id).bind(
                department -> Integer.toString(department.getId()),
                ((department, formValue) -> department.setId(Integer.parseInt(formValue)))
        );
        binder.forField(parent).bind(
                department -> "Nix",
                ((department, formValue) -> department.setParent(null))
        );
        radioButtonGroup.setValue("Ja");
        radioButtonGroup.addValueChangeListener(e -> {
            fl.setVisible(e.getValue().equals("Ja"));

            binder.readBean(d);
        });
        add(radioButtonGroup, fl);
    }

    public FormLayout createLayout() {
        FormLayout fl = new FormLayout();
        fl.add(name,manager,id,parent,save);
        save.addClickListener(buttonClickEvent -> {
            try {
                binder.writeBean(d);
                processData();
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            close();
        });
        return fl;
    }

    public void processData() {
        TreeDataProvider<Department> dataProvider = (TreeDataProvider<Department>) mainView.grid.getDataProvider();
        TreeData<Department> data = dataProvider.getTreeData();
        ArrayList<Department> departments = new ArrayList<>();
        departments.add(new Department(1,"Ousmane", null, "Osmane"));
        data.addItem(null, d);
        data.addItems(d, departments);
        dataProvider.refreshAll();
    }
}
