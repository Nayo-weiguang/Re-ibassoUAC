using System.Windows;
using iBassoUAC.ViewModels;

namespace iBassoUAC;

public partial class MainWindow : Window
{
    public MainWindow()
    {
        InitializeComponent();
        DataContext = new MainViewModel();
    }
}