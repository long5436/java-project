package controllers;

import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import view.ViewPanelWarehouse;
import model.WarehouseModel;
import entities.Warehouse;

public class WarehouseController {
    private ViewPanelWarehouse view;
    private WarehouseModel model;
    private ArrayList<Warehouse> arr;
    private int indexTableSelected;
    private int indexSort;

    public WarehouseController(ViewPanelWarehouse view, WarehouseModel model) {
        this.view = view;
        this.model = model;
        this.indexTableSelected = -1;
        this.indexSort = -1;
        this.arr = new ArrayList<Warehouse>();

        init();
    }

    private void init() {

        view.getTxtProductId().setEditable(false);
        view.getTxtProductName().setEditable(false);
        view.getTxtSearch().setEnabled(false);
        view.getBtnSearch().setEnabled(false);

        view.getTblWarehouseView().addMouseListener(tableListener());
        view.getBtnEditQuantity().addActionListener(handleEdit());
        view.getBtnSearch().addActionListener(handleSearch());
        view.getCheckSearch().addItemListener(handleCheckSearch());
        view.getCboSort().addItemListener(handleComboBoxSelect());

        loadData();
        renderTable();
    }

    private ItemListener handleComboBoxSelect() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent arg0) {

                indexSort = view.getCboSort().getSelectedIndex();
                renderTable();
            }
        };
    }

    private void sortWarehouse() {
        switch (indexSort) {
            case 1:
                arr = model.sortByName();
                break;
            case 2:
                arr = model.sortByQuantity();
                break;
            case 3:
                arr = model.sortByNameThenByQuantity();
                break;
            case 4:
                arr = model.sortByQuantityThenByName();
                break;
            case 0:
            default:
                // arr = model.getArr();
                break;
        }
    }

    private ItemListener handleCheckSearch() {
        return new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == 1) {
                } else {
                    loadData();
                    renderTable();
                }

                view.getTxtSearch().setEnabled(ie.getStateChange() == 1);
                view.getBtnSearch().setEnabled(ie.getStateChange() == 1);
            }
        };
    }

    private ActionListener handleSearch() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String keyword = view.getTxtSearch().getText();
                if (!keyword.equals("")) {
                    try {
                        arr = model.searchWarehouse(keyword);
                        renderTable();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm");
                    }
                }

            }
        };
    }

    private ActionListener handleEdit() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String id = view.getTxtProductId().getText();
                    boolean checkNumber = checkNumber(view.getTxtQuantity().getText());
                    if (checkNumber) {
                        int qty = Integer.parseInt(view.getTxtQuantity().getText());
                        Warehouse w = new Warehouse(id, qty);
                        boolean check = model.editWarehouse(id, w);

                        JOptionPane.showMessageDialog(view, check ? "Sửa thành công" : "Sửa không thành công");

                        if (check) {
                            loadData();
                            renderTable();
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Có lỗi, sửa không thành công");
                }
            }
        };
    }

    private void loadData() {
        try {
            arr = model.getWarehouse();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu");
        }
    }

    private MouseAdapter tableListener() {
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                int index = view.getTblWarehouseView().getSelectedRow();
                indexTableSelected = index;

                view.getTxtProductId().setText(arr.get(index).getProductId());
                view.getTxtProductName().setText(arr.get(index).getProductName());
                view.getTxtQuantity().setText(arr.get(index).getQuantity() + "");

            }
        };
    }

    private void renderTable() {
        DefaultTableModel table = (DefaultTableModel) view.getTblWarehouseView().getModel();
        table.setRowCount(0);
        sortWarehouse();
        for (Warehouse wh : arr) {
            Object[] ob = new Object[] { wh.getProductId(), wh.getProductName(), wh.getQuantity() };
            table.addRow(ob);
        }
    }

    private boolean checkNumber(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Trường số lượng phải là số"));
            JOptionPane.showMessageDialog(null, panel);

            return false;
        }
    }
}
