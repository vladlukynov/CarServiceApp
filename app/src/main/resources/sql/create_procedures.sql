USE CarService;

-- Хранимые процедуры
-- Хранимые процедуры с запросами, включающими одну таблицу

-- Таблица Users
-- 1. Изменение статуса учетной записи пользователя по логину пользователя
GO
CREATE PROCEDURE ChangeUserStatus
    @UserLogin VARCHAR(30),
    @Status BIT
AS
    UPDATE Users SET IsActive = @Status WHERE Users.UserLogin = @UserLogin;
GO

-- Таблица Cars
-- 2. Добавление записи о новом автомобиле
-- Входные данные: Manufacturer, CarModel, ReleaseYear
GO
CREATE PROCEDURE AddCar
    @Manufacturer VARCHAR(30),
    @CarModel VARCHAR(30),
    @ReleaseYear SMALLINT
AS
    INSERT INTO Cars (Manufacturer, CarModel, ReleaseYear)
        VALUES (@Manufacturer, @CarModel, @ReleaseYear);
GO

-- 3. Изменение статуса автомобиля по ID автомобиля
GO
CREATE PROCEDURE ChangeCarStatus
    @CarId INT,
    @Status BIT
AS
    UPDATE Cars SET IsActive = @Status WHERE Cars.CarId = @CarId;
GO

-- Таблица Services
-- 4. Добавление записи о новой услуге
-- Входные данные: ServiceName, Descpiption, Price
GO
CREATE PROCEDURE AddService
    @ServiceName NVARCHAR(50),
    @Description NVARCHAR(150),
    @Price MONEY
AS
    INSERT INTO Services (ServiceName, Description, Price)
        VALUES (@ServiceName, @Description, @Price);
GO

-- 5. Изменение информации об услуге (без изменения ID услуги) по ID услуги
-- Входные данные: ServiceName, Descpiption, Price
GO
CREATE PROCEDURE EditService
    @ServiceId INT,
    @ServiceName NVARCHAR(50),
    @Description NVARCHAR(150),
    @Price MONEY
AS
    UPDATE Services SET
        ServiceName = @ServiceName,
        Description = @Description,
        Price = @Price
    WHERE Services.ServiceId = @ServiceId;
GO

-- 6. Изменение статуса услуги по ID услуги
GO
CREATE PROCEDURE ChangeServiceStatus
    @ServiceId INT,
    @Status BIT
AS
    UPDATE Services SET IsActive = @Status WHERE Services.ServiceId = @ServiceId;
GO

-- Таблица Details
-- 7. Добавление записи о новой детали
-- Входные данные: DetailName, Price, Quantity
GO
CREATE PROCEDURE AddDetail
    @DetailName VARCHAR(30),
    @Price MONEY,
    @Quantity INT
AS
    INSERT INTO Details (DetailName, Price, Quantity)
        VALUES (@DetailName, @Price, @Quantity);
GO

-- 8. Изменение информации о детали (без изменения ID детали) по ID детали Входные данные: DetailName, Price, Quantity
GO
CREATE PROCEDURE EditDetail
    @DetailId INT,
    @DetailName VARCHAR(30),
    @Price MONEY,
    @Quantity INT
AS
    UPDATE Details SET
        DetailName = @DetailName,
        Price = @Price,
        Quantity = @Quantity
    WHERE Details.DetailId = @DetailId;
GO

-- 9. Вывод количества детали на складе по ID детали
GO
CREATE PROCEDURE GetDetailQuantity
    @DetailId INT
AS
    SELECT Quantity FROM Details WHERE Details.DetailId = @DetailId;
GO

-- 10. Изменение количества детали на складе по ID детали
-- Входные данные: Quantity
GO
CREATE PROCEDURE ChangeDetailQuantity
    @DetailId INT,
    @Quantity INT
AS
    UPDATE Details SET Quantity = @Quantity WHERE Details.DetailId = @DetailId;
GO

-- Таблица Orders
-- 11. Добавление записи о новом заказе
-- Входные данные: CarId, CarNumber, ClientLogin
GO
CREATE PROCEDURE AddOrder
    @CarId INT,
    @CarNumber NVARCHAR(9),
    @ClientLogin VARCHAR(30)
AS
    INSERT INTO Orders(CarId, CarNumber, ClientLogin)
        VALUES (@CarId, @CarNumber, @ClientLogin);
GO

-- 12. Изменение статуса заказа по ID заказа
-- Входные данные: Status
GO
CREATE PROCEDURE ChangeOrderStatus
    @OrderId INT,
    @Status BIT
AS
    UPDATE Orders SET Status = @Status WHERE Orders.OrderId = @OrderId;
GO

-- 13. Добавление сотрудника к заказу и изменение статуса этого заказа
-- Входные данные: ID сотрудника, Status
GO
CREATE PROCEDURE AddEmployeeToOrder
    @OrderId INT,
    @EmployeeLogin VARCHAR(30),
    @Status NVARCHAR(20)
AS
    UPDATE Orders SET
        EmployeeLogin = @EmployeeLogin,
        Status = @Status,
        StatusChangeDate = GETDATE()
    WHERE Orders.OrderId = @OrderId;
GO

-- Таблица OrdersServices
-- 14. Добавление записи в таблицу
-- Входные данные: ServiceId, OrderId, Quantity
GO
CREATE PROCEDURE AddServiceToOrder
    @ServiceId INT,
    @OrderId INT,
    @Quantity INT
AS
    INSERT INTO OrdersServices (OrderId, ServiceId, Quantity)
        VALUES (@OrderId, @ServiceId, @Quantity);
GO

-- 15. Удаление записи из таблицы
-- Входные данные: ServiceId, OrderId
GO
CREATE PROCEDURE DeleteServiceFromOrder
    @ServiceId INT,
    @OrderId INT
AS
    DELETE FROM OrdersServices
        WHERE OrderId = @OrderId AND ServiceId = @ServiceId;
GO

-- Таблица OrdersDetails
-- 16. Добавление записи в таблицу
-- Входные данные: DetailId, OrderId, Quantity
GO
CREATE PROCEDURE AddDetailToOrder
    @DetailId INT,
    @OrderId INT,
    @Quantity INT
AS
    INSERT INTO OrdersDetails (OrderId, DetailId, Quantity)
        VALUES (@OrderId, @DetailId, @Quantity);
GO

-- 17. Удаление записи из таблицы
-- Входные данные: DetailId, OrderId
GO
CREATE PROCEDURE DeleteDetailFromOrder
    @DetailId INT,
    @OrderId INT
AS
    DELETE FROM OrdersDetails
        WHERE OrderId = @OrderId AND DetailId = @DetailId;
GO

-- Хранимые процедуры с запросами, включающими несколько таблиц

-- 1. Регистрация нового сотрудника
-- Таблицы: Users, Employees
-- Входные данные: UserLogin, Pass, Email, PhoneNumber, RoleId, FirstName, LastName, MiddleName, Post, Salary, Birthday
GO
CREATE PROCEDURE RegisterEmployee
    @UserLogin VARCHAR(30),
    @Pass VARCHAR(32),
    @Email VARCHAR(30),
    @PhoneNumber VARCHAR(15),
    @FirstName NVARCHAR(20),
    @LastName NVARCHAR(20),
    @MiddleName NVARCHAR(20),
    @Post NVARCHAR(20),
    @Salary MONEY,
    @Birthday DATE
AS
    SET XACT_ABORT ON;
    BEGIN TRANSACTION;
        INSERT INTO Users (UserLogin, Pass, Email, PhoneNumber, RoleId)
            VALUES (@UserLogin, @Pass, @Email, @PhoneNumber, 2);
        INSERT INTO Employees (UserLogin, FirstName, LastName, MiddleName, Post, Salary, Birthday)
            VALUES (@UserLogin, @FirstName, @LastName, @MiddleName, @Post, @Salary, @Birthday);
    COMMIT;
GO

-- 2. Регистрация нового клиента
-- Таблицы: Users, Clients
-- Входные данные: UserLogin, Pass, Email, PhoneNumber, FirstName, LastName, MiddleName, Birthday
GO
CREATE PROCEDURE RegisterClient
    @UserLogin VARCHAR(30),
    @Pass VARCHAR(32),
    @Email VARCHAR(30),
    @PhoneNumber VARCHAR(15),
    @FirstName NVARCHAR(20),
    @LastName NVARCHAR(20),
    @MiddleName NVARCHAR(20),
    @Birthday Date
AS
    SET XACT_ABORT ON;
    BEGIN TRANSACTION;
        INSERT INTO Users (UserLogin, Pass, Email, PhoneNumber, RoleId)
            VALUES (@UserLogin, @Pass, @Email, @PhoneNumber, 3);
        INSERT INTO Clients (UserLogin, FirstName, LastName, MiddleName, Birthday)
            VALUES (@UserLogin, @FirstName, @LastName, @MiddleName, @Birthday);
    COMMIT;
GO

-- 3. Изменение информации о сотруднике и/или его учетных данных по логину сотрудника
-- Таблицы: Users, Employees
-- Входные данные: UserLogin, Pass, Email, PhoneNumber, RoleId, FirstName, LastName, MiddleName, Post, Salary, Birthday
GO
CREATE PROCEDURE EditEmployee
    @UserLogin VARCHAR(30),
    @Pass VARCHAR(32),
    @Email VARCHAR(30),
    @PhoneNumber VARCHAR(15),
    @RoleId INT,
    @FirstName NVARCHAR(20),
    @LastName NVARCHAR(20),
    @MiddleName NVARCHAR(20),
    @Post NVARCHAR(20),
    @Salary MONEY,
    @Birthday DATE
AS
    SET XACT_ABORT ON;
    BEGIN TRANSACTION;
        UPDATE Employees SET
            UserLogin = @UserLogin,
            FirstName = @FirstName,
            LastName = @LastName,
            MiddleName = @MiddleName,
            Post = @Post,
            Salary = @Salary,
            Birthday = @Birthday
        WHERE Employees.UserLogin = @UserLogin;
        UPDATE Users SET
            UserLogin = @UserLogin,
            Pass = @Pass,
            Email = @Email,
            PhoneNumber = @PhoneNumber,
            RoleId = @RoleId
        WHERE Users.UserLogin = @UserLogin;
    COMMIT;
GO

-- 4. Изменение информации о клиенте и/или его учетных данных по логину клиента
-- Таблицы: Users, Clients
-- Входные данные: UserLogin, Pass, Email, PhoneNumber, FirstName, LastName, MiddleName, Birthday
GO
CREATE PROCEDURE EditClient
    @UserLogin VARCHAR(30),
    @Pass VARCHAR(32),
    @Email VARCHAR(30),
    @PhoneNumber VARCHAR(15),
    @FirstName NVARCHAR(20),
    @LastName NVARCHAR(20),
    @MiddleName NVARCHAR(20),
    @Birthday DATE
AS
    SET XACT_ABORT ON;
    BEGIN TRANSACTION;
        UPDATE Clients SET
            UserLogin = @UserLogin,
            FirstName = @FirstName,
            LastName = @LastName,
            MiddleName = @MiddleName,
            Birthday = @Birthday
        WHERE Clients.UserLogin = @UserLogin;
        UPDATE Users SET
            UserLogin = @UserLogin,
            Pass = @Pass,
            Email = @Email,
            PhoneNumber = @PhoneNumber
        WHERE Users.UserLogin = @UserLogin;
    COMMIT;
GO

-- 5. Вывод заказов клиента с указанием использованных деталей и услуг и стоимости заказа, исходя из количества этих деталей и услуг
-- Таблицы: Orders, OrdersSerivces, OrdersDetails, Services, Details
GO
CREATE PROCEDURE GetClientOrdersWithSum
    @ClientLogin VARCHAR(30)
AS
    SELECT *, dbo.GetOrderSum(InfoTable.OrderId) as Total FROM
        (SELECT Orders.OrderId, CarId, CarNumber, Status, CreationDate, StatusChangeDate, EmployeeLogin,
                ServiceName as Element, OrdersServices.Quantity,
                (OrdersServices.Quantity * Services.Price) as ElementSum
        FROM Orders
            JOIN OrdersServices ON Orders.OrderId = OrdersServices.OrderId
            JOIN Services ON OrdersServices.ServiceId = Services.ServiceId
        WHERE Orders.ClientLogin = @ClientLogin
        UNION
        SELECT Orders.OrderId, CarId, CarNumber, Status, CreationDate, StatusChangeDate, EmployeeLogin,
               DetailName, OrdersDetails.Quantity, (OrdersDetails.Quantity * Details.Price)
        FROM Orders
            JOIN OrdersDetails ON Orders.OrderId = OrdersDetails.OrderId
            JOIN Details ON OrdersDetails.DetailId = Details.DetailId
        WHERE Orders.ClientLogin = @ClientLogin) as InfoTable;
GO

-- 6. Вывод информации об автомобилях, которые когда-либо обслуживал клиент, и сортировка по дате создания заказа
-- Таблицы: Orders, Cars
GO
CREATE PROCEDURE GetClientCars
    @ClientLogin VARCHAR(30)
AS
    SELECT Cars.Manufacturer, Cars.CarModel, Cars.ReleaseYear, Orders.CarNumber, Orders.CreationDate FROM Orders
        JOIN Cars ON Orders.CarId = Cars.CarId
    WHERE ClientLogin = @ClientLogin
    ORDER BY Orders.CreationDate;
GO

-- 7. Вывод списка сотрудников и количества выполненных ими заказов за определенный промежуток времени
-- Таблицы: Employees, Orders
GO
CREATE PROCEDURE GetEmployeesOrders
    @StartDate Date,
    @EndDate Date
AS
    IF @StartDate <= @EndDate
        SELECT UserLogin, FirstName, LastName, MiddleName, Post, Salary, Birthday, countTable.OrdersCount
        FROM Employees
            JOIN Orders ON Orders.EmployeeLogin = Employees.UserLogin,
            (SELECT EmployeeLogin, COUNT(*) as OrdersCount FROM Orders GROUP BY EmployeeLogin) as countTable
        WHERE StatusChangeDate >= @StartDate AND StatusChangeDate <= @EndDate AND Status = N'Завершен'
            AND countTable.EmployeeLogin = Orders.EmployeeLogin;
GO

-- 8. Расчет суммы заказа, исходя из количества использованных деталей и из количества оказанных услуг
-- Таблицы: Orders, OrdersServices, OrdersDetails, Details, Services
-- Входные данные: ID заказа
GO
CREATE FUNCTION GetOrderSum (@OrderId INT)
RETURNS INT
BEGIN
    DECLARE @DetailsSum INT;
    DECLARE @ServicesSum INT;

    SET @DetailsSum = (SELECT SUM(Details.Price * OrdersDetails.Quantity) FROM Orders
                           JOIN OrdersDetails ON Orders.OrderId = OrdersDetails.OrderId
                           JOIN Details ON OrdersDetails.DetailId = Details.DetailId
                       WHERE Orders.OrderId = @OrderId);

    SET @ServicesSum = (SELECT SUM(Services.Price * OrdersServices.Quantity) FROM Orders
                           JOIN OrdersServices ON Orders.OrderId = OrdersServices.OrderId
                           JOIN Services ON OrdersServices.ServiceId = Services.ServiceId
                        WHERE Orders.OrderId = @OrderId);

    RETURN @ServicesSum + @DetailsSum;
END;
GO

-- 9. Добавление детали к заказу и уменьшение количества оставшихся деталей на складе Таблицы: OrdersDetails, Details
-- Входные данные: ID заказа, ID детали, Quantity
GO
CREATE PROCEDURE AddDetailToOrder2
    @OrderId INT,
    @DetailId INT,
    @Quantity INT
AS
    SET XACT_ABORT ON;
    BEGIN TRANSACTION;
        DECLARE @DetailQuantity INT;
        DECLARE @DeleteDetailQuantity INT;

        SET @DetailQuantity = (SELECT Quantity FROM Details WHERE DetailId = @DetailId);
        SET @DeleteDetailQuantity = (SELECT Quantity FROM OrdersDetails WHERE OrderId = @OrderId AND DetailId = @DetailId);

        INSERT INTO OrdersDetails (OrderId, DetailId, Quantity)
            VALUES (@OrderId, @DetailId, @Quantity);
        UPDATE Details SET Quantity = @DetailQuantity - @DeleteDetailQuantity WHERE DetailId = @DetailId;
    COMMIT;
GO

-- 10. Удаление детали из заказа и увеличение количества оставшихся деталей на складе Таблицы: OrdersDetails, Details
-- Входные данные: ID заказа, ID детали
GO
CREATE PROCEDURE DeleteDetailFromOrder2
    @OrderId INT,
    @DetailId INT
AS
    SET XACT_ABORT ON;
    BEGIN TRANSACTION;
        DECLARE @DetailQuantity INT;
        DECLARE @DeleteDetailQuantity INT;

        SET @DetailQuantity = (SELECT Quantity FROM Details WHERE DetailId = @DetailId);
        SET @DeleteDetailQuantity = (SELECT Quantity FROM OrdersDetails WHERE OrderId = @OrderId AND DetailId = @DetailId);

        DELETE FROM OrdersDetails WHERE OrderId = @OrderId AND DetailId = @DetailId;
        UPDATE Details SET Quantity = @DetailQuantity + @DeleteDetailQuantity WHERE DetailId = @DetailId;
    COMMIT;
GO
