from appium import webdriver
import time

caps = {}
caps["platformName"] = "Android"
caps["platformVersion"] = "6.0"
caps["deviceName"] = "OPPO R9s"
caps["appActivity"] = ".MainActivity"
caps["appPackage"] = "com.example.test.myapplication"
caps["autoGrantPermissions"] = "true"
driver = webdriver.Remote("http://localhost:4723/wd/hub", caps)



el1 = driver.find_element_by_id("com.example.test.myapplication:id/et_name")
el1.send_keys("21816636")

el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_pass")
el2.send_keys("wyhcb18276")

el3 = driver.find_element_by_id("com.example.test.myapplication:id/login")
el3.click()

#To Test registration screen of UWA_Parking
el1 = driver.find_element_by_id("com.example.test.uwa_parking:id/et_studentno")
el1 = driver.find_element_by_id("android:id/action_bar")
el1.click()

el1.send_keys("21816636")
el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_pass")
el2.send_keys("******")
el2.click()
el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_repass")
el2.send_keys("******")
el2.click()
el3 = driver.find_element_by_id("com.example.test.myapplication:id/et_emailid")
el3.send_keys("21816636@student.uwa.edu.au")
el3.click()
el4 = driver.find_element_by_id("com.example.test.myapplication:id/et_student")
el4.click()
el5 = driver.find_element_by_id("com.example.test.myapplication:id/et_Yellow ")
el5.click()
el5 = driver.find_element_by_id("com.example.test.myapplication:id/et_signin ")
el5.click()

#To Test registration screen of UWA_Parking for Staff
el1 = driver.find_element_by_id("com.example.test.uwa_parking:id/et_studentno")
el1 = driver.find_element_by_id("android:id/action_bar")
el1.click()
el1.send_keys("123456")
el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_pass")
el2.send_keys("******")
el2.click()
el3 = driver.find_element_by_id("com.example.test.myapplication:id/et_repass")
el3.send_keys("******")
el3.click()
el4 = driver.find_element_by_id("com.example.test.myapplication:id/et_emailid")
el4.send_keys("123456@student.uwa.edu.au")
el4.click()
el5 = driver.find_element_by_id("com.example.test.myapplication:id/et_staff")
el5.click()
el6 = driver.find_element_by_id("com.example.test.myapplication:id/et_Red ")
el6.click()
el7 = driver.find_element_by_id("com.example.test.myapplication:id/et_signin ")
el7.click()

#To Test registration screen of GPS_Cordinates inside Geo fencing
el1 = driver.find_element_by_id("com.example.test.uwa_parking:id/et_latitude")
el1 = driver.find_element_by_id("android:id/action_bar")
el1.click()
el1.send_keys("******")
el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_longitude")
el2.send_keys("******")
el2.click()
el3 = driver.find_element_by_id("com.example.test.myapplication:id/et_in")
el3.click()
el4 = driver.find_element_by_id("com.example.test.myapplication:id/et_showmap")
el4.click()
el5 = driver.find_element_by_id("com.example.test.myapplication:id/et_showgps")
el5.click()

#To Test registration screen of GPS_Cordinates Outside Geo fencing:

el1 = driver.find_element_by_id("com.example.test.uwa_parking:id/et_latitude")
el1 = driver.find_element_by_id("android:id/action_bar")
el1.click()
el1.send_keys("******")
el2 = driver.find_element_by_id("com.example.test.myapplication:id/et_longitude")
el2.send_keys("******")
el2.click()
el3 = driver.find_element_by_id("com.example.test.myapplication:id/et_out")
el3.click()
el4 = driver.find_element_by_id("com.example.test.myapplication:id/et_showmap")
el4.click()

driver.quit()