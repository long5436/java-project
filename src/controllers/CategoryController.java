/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import entities.Category;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import javax.swing.JOptionPane;
import model.Model;
import view.ViewPanelCategory;
import utils.MethodUtil;

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
    private int indexSort;
    private int indexTableSelect;

    public CategoryController(ViewPanelCategory view, Model model) {
        this.model = model;
        this.view = view;
        this.listData = new ArrayList<>();
        this.indexSort = 0;
        this.indexTableSelect = -1;

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

        view.getTblCategoryView().addMouseListener(tableListener());
        view.getBtnToggleAdd().addActionListener(handleToggleAdd());
        view.getBtnAdd().addActionListener(handleAdd());
        view.getBtnToggleEdit().addActionListener(handleToggleEdit());
        view.getBtnEdit().addActionListener(handleEdit());
        view.getBtnDelete().addActionListener(handleDelete());
        view.getBtnSearch().addActionListener(handleSearch());

        view.getCboSort2().addItemListener(handleComboBoxSelect());
        view.getCheckSearch().addItemListener(handleCheckSearch());

        loadData();
        renderTable();

    }

    private ActionListener handleSearch() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyWord = view.getTxtSearch().getText();
                if (!keyWord.equals("")) {
                    try {
                        listData = model.getCategoryModel().searchCategory(keyWord);
                        renderTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm");
                    }
                }
            }
        };
    }

    private ItemListener handleCheckSearch() {
        return new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == 1) {
                    indexSort = 0;
                    view.getCboSort2().setSelectedIndex(0);
                } else {
                    view.getTxtSearch().setText("");
                    loadData();
                    renderTable();
                }

                view.getBtnToggleAdd().setEnabled(ie.getStateChange() != 1);
                view.getBtnSearch().setEnabled(ie.getStateChange() == 1);
                view.getTxtSearch().setEditable(ie.getStateChange() == 1);
            }
        };
    }

    private ItemListener handleComboBoxSelect() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {

                indexSort = view.getCboSort2().getSelectedIndex();
                loadData();

                renderTable();
            }
        };
    }

    private ActionListener handleDelete() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int confirm = MethodUtil.showMessageConfirm("Bạn có chắc muốn xóa không");
                if (confirm == 0) {

                    int index = view.getTblCategoryView().getSelectedRow();
                    String id = listData.get(index).getCategoryId();

                    try {
                        Boolean check = model.getCategoryModel().deleteCategory(id);

                        handleAfterChangeData();
                        JOptionPane.showMessageDialog(view, check ? "Xoá thành công" : "Xoá không thành công");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Có lỗi xãy ra");
                    }
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
                        boolean check = model.getCategoryModel().editCategory(id, cate);

                        handleAfterChangeData();

                        editStatus = false;

                        view.getBtnEdit().setEnabled(editStatus);
                        view.getBtnToggleEdit().setText("Sủa");

                        JOptionPane.showMessageDialog(view, check ? "Sửa thành công" : "Sửa không thành công");

                    } catch (Exception ex) {
                        JOptionPane.showConfirmDialog(view, "Có lỗi xẫy ra");
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
                        boolean check = model.getCategoryModel().addCategory(cate);

                        handleAfterChangeData();

                        addStatus = false;
                        view.getBtnAdd().setEnabled(addStatus);
                        view.getBtnToggleAdd().setText("Sủa");

                        JOptionPane.showMessageDialog(view, check ? "Thêm thành công" : "Thêm không thành công");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Có lỗi xãy ra");
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
                    loadDataToField(indexTableSelect);
                } else {
                    addStatus = true;
                    editStatus = false;
                    view.getBtnToggleAdd().setText("Huỷ thêm");
                    loadDataToField(-1);
                }
                view.getBtnDelete().setEnabled(!addStatus && indexTableSelect >= 0);
                view.getBtnToggleEdit().setEnabled(editStatus);
                view.getTxtCategoryName().setEditable(addStatus);
                view.getTxtCategoryId().setEditable(addStatus);
                view.getBtnAdd().setEnabled(addStatus);
                // view.getTxtCategoryId().setFocusable(addStatus);
            }
        };
    }

    private void loadDataToField(int index) {
        boolean check = index >= 0;

        view.getTxtCategoryId().setText(check ? listData.get(index).getCategoryId() : "");
        view.getTxtCategoryName().setText(check ? listData.get(index).getCategoryName() : "");
    }

    private MouseAdapter tableListener() {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = view.getTblCategoryView().getSelectedRow();
                indexTableSelect = index;
                loadDataToField(index);

                view.getBtnToggleEdit().setEnabled(index >= 0);
                view.getBtnDelete().setEnabled(index >= 0);

                addStatus = false;
                view.getBtnToggleAdd().setText("Thêm mới");
                view.getBtnAdd().setEnabled(false);
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

            switch (indexSort) {
                case 1:
                    listData = model.getCategoryModel().sortByAZ();
                    break;
                case 2:
                    listData = model.getCategoryModel().sortByZA();
                    break;
                case 0:
                default:
                    listData = model.getCategoryModel().getCategories();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Không thể tải dữ liệu, có lỗi xãy ra");
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
