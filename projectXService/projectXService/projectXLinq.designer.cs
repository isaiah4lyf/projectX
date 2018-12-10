﻿#pragma warning disable 1591
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace projectXService
{
	using System.Data.Linq;
	using System.Data.Linq.Mapping;
	using System.Data;
	using System.Collections.Generic;
	using System.Reflection;
	using System.Linq;
	using System.Linq.Expressions;
	using System.ComponentModel;
	using System;
	
	
	[global::System.Data.Linq.Mapping.DatabaseAttribute(Name="projectXService")]
	public partial class projectXLinqDataContext : System.Data.Linq.DataContext
	{
		
		private static System.Data.Linq.Mapping.MappingSource mappingSource = new AttributeMappingSource();
		
    #region Extensibility Method Definitions
    partial void OnCreated();
    partial void InsertSession(Session instance);
    partial void UpdateSession(Session instance);
    partial void DeleteSession(Session instance);
    #endregion
		
		public projectXLinqDataContext() : 
				base(global::System.Configuration.ConfigurationManager.ConnectionStrings["projectXServiceConnectionString"].ConnectionString, mappingSource)
		{
			OnCreated();
		}
		
		public projectXLinqDataContext(string connection) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public projectXLinqDataContext(System.Data.IDbConnection connection) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public projectXLinqDataContext(string connection, System.Data.Linq.Mapping.MappingSource mappingSource) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public projectXLinqDataContext(System.Data.IDbConnection connection, System.Data.Linq.Mapping.MappingSource mappingSource) : 
				base(connection, mappingSource)
		{
			OnCreated();
		}
		
		public System.Data.Linq.Table<Session> Sessions
		{
			get
			{
				return this.GetTable<Session>();
			}
		}
	}
	
	[global::System.Data.Linq.Mapping.TableAttribute(Name="dbo.Sessions")]
	public partial class Session : INotifyPropertyChanging, INotifyPropertyChanged
	{
		
		private static PropertyChangingEventArgs emptyChangingEventArgs = new PropertyChangingEventArgs(String.Empty);
		
		private int _SessionId;
		
		private string _CurrentQuestion;
		
		private string _CurrentAnswer;
		
		private System.Nullable<int> _CurrentRemainingTime;
		
		private System.Nullable<int> _CurrentNumberOfUsers;
		
    #region Extensibility Method Definitions
    partial void OnLoaded();
    partial void OnValidate(System.Data.Linq.ChangeAction action);
    partial void OnCreated();
    partial void OnSessionIdChanging(int value);
    partial void OnSessionIdChanged();
    partial void OnCurrentQuestionChanging(string value);
    partial void OnCurrentQuestionChanged();
    partial void OnCurrentAnswerChanging(string value);
    partial void OnCurrentAnswerChanged();
    partial void OnCurrentRemainingTimeChanging(System.Nullable<int> value);
    partial void OnCurrentRemainingTimeChanged();
    partial void OnCurrentNumberOfUsersChanging(System.Nullable<int> value);
    partial void OnCurrentNumberOfUsersChanged();
    #endregion
		
		public Session()
		{
			OnCreated();
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_SessionId", AutoSync=AutoSync.OnInsert, DbType="Int NOT NULL IDENTITY", IsPrimaryKey=true, IsDbGenerated=true)]
		public int SessionId
		{
			get
			{
				return this._SessionId;
			}
			set
			{
				if ((this._SessionId != value))
				{
					this.OnSessionIdChanging(value);
					this.SendPropertyChanging();
					this._SessionId = value;
					this.SendPropertyChanged("SessionId");
					this.OnSessionIdChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_CurrentQuestion", DbType="NVarChar(MAX)")]
		public string CurrentQuestion
		{
			get
			{
				return this._CurrentQuestion;
			}
			set
			{
				if ((this._CurrentQuestion != value))
				{
					this.OnCurrentQuestionChanging(value);
					this.SendPropertyChanging();
					this._CurrentQuestion = value;
					this.SendPropertyChanged("CurrentQuestion");
					this.OnCurrentQuestionChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_CurrentAnswer", DbType="NVarChar(MAX)")]
		public string CurrentAnswer
		{
			get
			{
				return this._CurrentAnswer;
			}
			set
			{
				if ((this._CurrentAnswer != value))
				{
					this.OnCurrentAnswerChanging(value);
					this.SendPropertyChanging();
					this._CurrentAnswer = value;
					this.SendPropertyChanged("CurrentAnswer");
					this.OnCurrentAnswerChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_CurrentRemainingTime", DbType="Int")]
		public System.Nullable<int> CurrentRemainingTime
		{
			get
			{
				return this._CurrentRemainingTime;
			}
			set
			{
				if ((this._CurrentRemainingTime != value))
				{
					this.OnCurrentRemainingTimeChanging(value);
					this.SendPropertyChanging();
					this._CurrentRemainingTime = value;
					this.SendPropertyChanged("CurrentRemainingTime");
					this.OnCurrentRemainingTimeChanged();
				}
			}
		}
		
		[global::System.Data.Linq.Mapping.ColumnAttribute(Storage="_CurrentNumberOfUsers", DbType="Int")]
		public System.Nullable<int> CurrentNumberOfUsers
		{
			get
			{
				return this._CurrentNumberOfUsers;
			}
			set
			{
				if ((this._CurrentNumberOfUsers != value))
				{
					this.OnCurrentNumberOfUsersChanging(value);
					this.SendPropertyChanging();
					this._CurrentNumberOfUsers = value;
					this.SendPropertyChanged("CurrentNumberOfUsers");
					this.OnCurrentNumberOfUsersChanged();
				}
			}
		}
		
		public event PropertyChangingEventHandler PropertyChanging;
		
		public event PropertyChangedEventHandler PropertyChanged;
		
		protected virtual void SendPropertyChanging()
		{
			if ((this.PropertyChanging != null))
			{
				this.PropertyChanging(this, emptyChangingEventArgs);
			}
		}
		
		protected virtual void SendPropertyChanged(String propertyName)
		{
			if ((this.PropertyChanged != null))
			{
				this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
			}
		}
	}
}
#pragma warning restore 1591
