#Designed by Chong Xie 21885263
#This is for history inoformation
import mysql.connector;
import matplotlib.pyplot as plt;
import datetime;
import os;
import sys;
#Node.js execute calling cause current working directory is heat_map_visualization 
print(os.getcwd());

#SQL configuration
mydb = mysql.connector.connect(
    host="106.14.213.85",
    user="root",
    password="",
    port="3306",
    database="uwa_parking");

#define the range
current_day=datetime.datetime.now();
current_day=datetime.datetime(current_day.year,current_day.month,current_day.day,0,0,0);
current_day=current_day.strftime("%Y-%m-%d %H:%M:%S");
current_now=datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S");

#execute query command
mycursor = mydb.cursor();
query_command="SELECT time FROM parking_record WHERE (time BETWEEN '"+current_day+"' AND '"+current_now+"')";
#print(query_command);
mycursor.execute(query_command)

#get a tuple result
result = mycursor.fetchall();

#print(result);
count=0;
#name tag
#result[0] are datetime type, convert it to date string type
name_list= [];
for i in result:
    name_list.append(i[0].strftime('%Y-%m-%d-%H'));
#print(name_list);
#calculate the parking data every hour
dict_statistic={};
for key in name_list:
    dict_statistic[key] = dict_statistic.get(key,0)+1;
#print(dict_statistic);
name_list_plot=[];
num_list_plot=[];
#get ready for ploting library api
for key, value in dict_statistic.items():
    name_list_plot.append(key);
    num_list_plot.append(value);
#plot it to the map
plt.bar(range(len(name_list_plot)), num_list_plot,color='rgb',tick_label=name_list_plot);
#due to working directory change, the relative path will be changed.
#If you only test this script please comment plt.savefig() command.
plt.savefig("./public/image/s.png");
#plt.show()
    
