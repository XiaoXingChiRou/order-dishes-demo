package com.szx.orderdishesdemo.ui;


import com.szx.orderdishesdemo.model.Dish;
import com.szx.orderdishesdemo.service.DishService;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * @ Tool：IntelliJ IDEA
 * @ Author：XiaoXing
 * @ Date：2025-03-11 09:37
 * @ Description：
 */
@Component
public class DishSwingUI {

    private final DishService dishService;

    public DishSwingUI(DishService dishService) {
        this.dishService = dishService;
    }

    public void createAndShowUI() {
        JFrame frame = new JFrame("菜品选择器");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);

        // 按钮区域
        JButton addButton = new JButton("添加菜品");
        JButton randomButton = new JButton("随机选择菜品");
        JButton displayButton = new JButton("显示所有菜品");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(displayButton);

        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        // 按钮事件

        // 添加菜品：弹窗中选择已有分类或新建分类，再输入菜品名称
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDishDialog();
            }
        });

        // 随机选择菜品：弹窗中选择分类后，随机抽取两个菜品
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                randomSelectDialog();
            }
        });

        // 显示所有菜品：弹窗中按分类显示所有菜品
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllDishes();
            }
        });

        frame.setVisible(true);
    }

    // 弹窗添加菜品
    private void addDishDialog() {
        // 获取当前分类列表
        Map<String, List<Dish>> grouped = dishService.getDishesGroupedByCategory();
        String[] categories = grouped.keySet().toArray(new String[0]);

        // 下拉框，列出已有分类及“新建分类”
        JComboBox<String> categoryCombo = new JComboBox<>();
        for (String cat : categories) {
            categoryCombo.addItem(cat);
        }
        categoryCombo.addItem("新建分类");

        JTextField newCategoryField = new JTextField(10);
        newCategoryField.setEnabled(false);

        // 当选中“新建分类”时，启用新分类输入框
        categoryCombo.addActionListener(e -> {
            if ("新建分类".equals(categoryCombo.getSelectedItem())) {
                newCategoryField.setEnabled(true);
            } else {
                newCategoryField.setEnabled(false);
            }
        });

        JTextField dishNameField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("选择分类:"));
        panel.add(categoryCombo);
        panel.add(new JLabel("新分类名称:"));
        panel.add(newCategoryField);
        panel.add(new JLabel("菜品名称:"));
        panel.add(dishNameField);

        int result = JOptionPane.showConfirmDialog(null, panel, "添加菜品", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String selectedCategory = (String) categoryCombo.getSelectedItem();
            String category = selectedCategory;
            if ("新建分类".equals(selectedCategory)) {
                category = newCategoryField.getText().trim();
                if (category.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "新分类名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            String dishName = dishNameField.getText().trim();
            if (dishName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "菜品名称不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dishService.addDish(category, dishName);
            JOptionPane.showMessageDialog(null, "添加成功！", "信息", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 弹窗随机选择两个菜品
    private void randomSelectDialog() {
        Map<String, List<Dish>> grouped = dishService.getDishesGroupedByCategory();
        if (grouped.isEmpty()) {
            JOptionPane.showMessageDialog(null, "无菜品数据，请先添加菜品！", "信息", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] categories = grouped.keySet().toArray(new String[0]);
        String selectedCategory = (String) JOptionPane.showInputDialog(null, "请选择分类：", "随机选择菜品", JOptionPane.PLAIN_MESSAGE, null, categories, categories[0]);
        if (selectedCategory == null) return;
        List<Dish> randomDishes = dishService.getRandomTwoDishes(selectedCategory);
        if (randomDishes.size() < 2) {
            JOptionPane.showMessageDialog(null, selectedCategory + " 的菜品不足2个！", "信息", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String message = "在分类 [" + selectedCategory + "] 中随机选择的两个菜品为:\n";
        message += randomDishes.get(0).getName() + "\n" + randomDishes.get(1).getName();
        JOptionPane.showMessageDialog(null, message, "随机选择结果", JOptionPane.INFORMATION_MESSAGE);
    }

    // 弹窗显示所有菜品（按分类分组）
    private void displayAllDishes() {
        Map<String, List<Dish>> grouped = dishService.getDishesGroupedByCategory();
        if (grouped.isEmpty()) {
            JOptionPane.showMessageDialog(null, "无菜品数据，请先添加菜品！", "信息", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String category : grouped.keySet()) {
            sb.append("[").append(category).append("]\n");
            for (Dish dish : grouped.get(category)) {
                sb.append(" - ").append(dish.getName()).append("\n");
            }
            sb.append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "所有菜品", JOptionPane.INFORMATION_MESSAGE);
    }
}
