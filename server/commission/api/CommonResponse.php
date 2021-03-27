<?php
	/**
	 * Common Response Class
	 */
	class CommonResponse 
	{
		private $response;
		private $data = [];
		private $message;

		function __construct($response, $data, $message)
		{
			$this->response = $response;
			$this->data = $data;
			$this->message = $message;
		}

		function setResponse($response) {
			$this->response = $response;
		}

		function setData($data) {
			$this->data = $data;
		}

		function getRespose() {
			return $this->response;
		}

		function getData() {
			return $this->data;
		}

		function toJson() {
			$json = [];
			$json['response'] = $this->response;
			$json['data'] = $this->data;
			$json['message'] = $this->message;
			return $json;
		}
	}

?>
