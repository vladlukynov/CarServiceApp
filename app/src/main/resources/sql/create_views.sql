USE CarService;
-- 1. Вывод списка сотрудников с количеством выполненных ими заказов за последний год, отсортированный по убыванию количества выполненных заказов
GO
CREATE VIEW GetEmployeesYearsOrders
AS
    SELECT UserLogin, FirstName, LastName, MiddleName, Post, Salary, Birthday, countTable.OrdersCount
    FROM Employees
            JOIN Orders ON Orders.EmployeeLogin = Employees.UserLogin,
        (SELECT EmployeeLogin, COUNT(*) as OrdersCount FROM Orders GROUP BY EmployeeLogin) as countTable
    WHERE DATEDIFF(dayofyear, Orders.StatusChangeDate , GETDATE()) <= 365 AND Status = N'Завершен'
        AND countTable.EmployeeLogin = Orders.EmployeeLogin
    ORDER BY countTable.OrdersCount DESC
    OFFSET 0 ROWS;
GO

-- 2. Вывод информации о клиентах с их учетными данными
GO
CREATE VIEW GetClientsInfo
AS
    SELECT Users.UserLogin, Pass, Email, PhoneNumber, RoleId, IsActive, FirstName, LastName, MiddleName, Birthday FROM Clients
        JOIN Users ON Clients.UserLogin = Users.UserLogin;
GO

-- 3. Вывод информации о сотрудниках с их учетными данными
GO
CREATE VIEW GetEmployeesInfo
AS
    SELECT Users.UserLogin, Pass, Email, PhoneNumber, RoleId, IsActive, FirstName, LastName, MiddleName, Post, Salary, Birthday FROM Employees
        JOIN Users ON Employees.UserLogin = Users.UserLogin;
GO

-- 4. Вывод информации об автомобилях
GO
CREATE VIEW GetCarsInfo
AS
    SELECT * FROM Cars;
GO

-- 5. Вывод информации об услугах
GO
CREATE VIEW GetServicesInfo
AS
    SELECT * FROM Services;
GO

-- 6. Вывод информации о деталях
GO
CREATE VIEW GetDetailsInfo
AS
    SELECT * FROM Details;
GO

-- 7. Вывод информации обо всех заказах с информацией об использованных деталях и оказанных услугах и стоимостью заказа
GO
CREATE VIEW GetOrdersWithSum
AS
    SELECT *, dbo.GetOrderSum(InfoTable.OrderId) as Total FROM
        (SELECT Orders.OrderId, CarId, CarNumber, Status, CreationDate, StatusChangeDate, EmployeeLogin,
            ServiceName as Element, OrdersServices.Quantity, (OrdersServices.Quantity * Services.Price) as ElementSum
        FROM Orders
            JOIN OrdersServices ON Orders.OrderId = OrdersServices.OrderId
            JOIN Services ON OrdersServices.ServiceId = Services.ServiceId
        UNION
        SELECT Orders.OrderId, CarId, CarNumber, Status, CreationDate, StatusChangeDate, EmployeeLogin,
            DetailName, OrdersDetails.Quantity, (OrdersDetails.Quantity * Details.Price)
        FROM Orders
            JOIN OrdersDetails ON Orders.OrderId = OrdersDetails.OrderId
            JOIN Details ON OrdersDetails.DetailId = Details.DetailId) as InfoTable;
GO
