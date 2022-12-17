/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import entities.Category;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import model.Model;
import view.ViewPanelCategory;

/**
 *
 * @author Long
 */
public class CategoryController {

    private Model model;
    private ViewPanelCategory view;
    private ArrayList<Category> listData;
    private boolean editStatus;
    private boolean addStatus;

    public CategoryController(ViewPanelCategory view, Model model) {
        this.model = model;
        this.view = view;
        this.listData = new ArrayList<>();

        init();
    }

    private void init() {

        this.editStatus = false;
        this.addStatus = false;

        view.getBtnAdd().setEnabled(false);
        view.getBtnEdit().setEnabled(false);
        view.getBtnSearch().setEnabled(false);
        view.getBtnDelete().setEnabled(false);
        view.getBtnToggleAdd().setEnabled(true);
        view.getBtnToggleEdit().setEnabled(false);

        view.getTxtCategoryName().setEditable(false);
        view.getTxtCategoryId().setEditable(false);

        loadData();
        renderTable();

        view.getTblCategoryView().addMouseListener(tableListener());
        view.getBtnToggleAdd().addActionListener(handleToggleAdd());
        view.getBtnAdd().addActionListener(handleAdd());
        view.getBtnToggleEdit().addActionListener(handleToggleEdit());
        view.getBtnEdit().addActionListener(handleEdit());
        view.getBtnDelete().addActionListener(handleDelete());

    }

    private ActionListener handleDelete() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = view.getTblCategoryView().getSelectedRow();
                String id = listData.get(index).getCategoryId();

                try {
                    model.getCategoryModel().deleteCategory(id);

                    handleAfterChangeData();

                } catch (Exception ex) {
                    System.out.println("Có lỗi xãy ra");
                }
            }
        };
    }

    private ActionListener handleEdit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getTxtCategoryId().getText();
                String name = view.getTxtCategoryName().getText();

                if (!id.equals("") && !name.equals("")) {
                    Category cate = new Category(id, name);

                    try {
                        model.getCategoryModel().editCategory(id, cate);

                        handleAfterChangeData();

                        editStatus = false;

                        view.getBtnEdit().setEnabled(editStatus);
                        view.getBtnToggleEdit().setText("Sủa");

                    } catch (Exception ex) {
                        System.out.println("Có lỗi xãy ra");
                    }

                }
            }
        };
    }

    private ActionListener handleAdd() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = view.getTxtCategoryId().getText();
                String name = view.getTxtCategoryName().getText();

                if (!id.equals("") && !name.equals("")) {
                    Category cate = new Category(id, name);

                    try {
                        model.getCategoryModel().addCategory(cate);

                        handleAfterChangeData();

                        addStatus = false;
                        view.getBtnAdd().setEnabled(addStatus);
                        view.getBtnToggleAdd().setText("Sủa");

                    } catch (Exception ex) {
                        System.out.println("Có lỗi xãy ra");
                    }
                }
            }
        };
    }

    private ActionListener handleToggleEdit() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editStatus) {
                    editStatus = false;
                    view.getBtnToggleEdit().setText("Sủa");
                } else {
                    editStatus = true;
                    view.getBtnToggleEdit().setText("Huỷ sửa");
                }
                view.getTxtCategoryName().setEditable(editStatus);
                // view.getTxtCategoryId().setEditable(editStatus);
                view.getBtnEdit().setEnabled(editStatus);
            }
        };
    }

    private ActionListener handleToggleAdd() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addStatus) {
                    addStatus = false;
                    view.getBtnToggleAdd().setText("Thêm mới");
                } else {
                    addStatus = true;
                    view.getBtnToggleAdd().setText("Huỷ thêm");
                }
                view.getTxtCategoryName().setEditable(addStatus);
                view.getTxtCategoryId().setEditable(addStatus);
                view.getBtnAdd().setEnabled(addStatus);
                // view.getTxtCategoryId().setFocusable(addStatus);
            }
        };
    }

    public MouseAdapter tableListener() {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int index = view.getTblCategoryView().getSelectedRow();
                boolean check = index >= 0;

                view.getTxtCategoryId().setText(check ? listData.get(index).getCategoryId() : "");
                view.getTxtCategoryName().setText(check ? listData.get(index).getCategoryName() : "");

                view.getBtnToggleEdit().setEnabled(check);
                view.getBtnDelete().setEnabled(check);
            }
        };
    }

    private void handleAfterChangeData() {
        loadData();
        renderTable();

        view.getTxtCategoryId().setText("");
        view.getTxtCategoryName().setText("");
        view.getTxtCategoryName().setEditable(false);
        view.getTxtCategoryId().setEditable(false);
    }

    private void loadData() {
        try {
            listData = model.getCategoryModel().getCategories();
        } catch (Exception e) {
            System.out.println("Có lỗi xãy ra");
        }
    }

    private void renderTable() {
        DefaultTableModel table = (DefaultTableModel) view.getTblCategoryView().getModel();
        table.setRowCount(0);

        for (Category cate : listData) {
            Object[] ob = new Object[] { cate.getCategoryId(), cate.getCategoryName() };
            table.addRow(ob);
        }
    }
}
