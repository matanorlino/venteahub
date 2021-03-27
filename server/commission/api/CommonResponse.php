<?php
	/**
	 * Common Response Class
	 */
	class CommonResponse 
	{
		private $response;
		private $data = [];

		function __construct($response, $data)
		{
			$this->response = $response;
			$this->data = $data;
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
			return $json;
		}
	}

?>
