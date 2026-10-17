Test Input :Show the top 10 customers by total salary for last month. Include customer name and total salary,
calculate the SUM of salary,
where salary is greater than 50000, group the results by customer, and sort by total salary in descending order.

Package structure

com.nl2sql
│
├── metadata
├── intent
├── retrieval
├── sql
├── pipeline
├── vector
├── prompt
├── common
└── config


com.nl2sql
│
├── api
│   ├── controller
│   ├── request
│   └── response
│
├── config
│
├── common
│   ├── exception
│   ├── util
│   ├── constants
│   ├── mapper
│   └── validation
│
├── pipeline
│   ├── PipelineExecutor
│   ├── PipelineStep
│   ├── SqlGenerationContext
│   └── step
│
├── intent
│   ├── analyzer
│   ├── model
│   ├── prompt
│   └── parser
│
├── retrieval
│   ├── table
│   ├── column
│   ├── relationship
│   └── vector
│
├── metadata
│   ├── model
│   ├── parser
│   ├── generator
│   └── repository
│
├── prompt
│   ├── builder
│   ├── template
│   └── loader
│
├── sql
│   ├── generator
│   ├── validator
│   ├── context
│   ├── prompt
│   └── executor
│
├── vector
│   ├── service
│   ├── document
│   ├── embedding
│   └── repository
│
├── llm
│   ├── chat
│   ├── embedding
│   ├── prompt
│   └── response
│
├── service
│
└── model