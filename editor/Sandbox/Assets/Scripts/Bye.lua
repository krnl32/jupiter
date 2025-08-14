function onInit()
	print("Initialized Bye Script")

	if entity:hasComponent("RigidBody2DComponent") then
		print("Entity Has RigidBody2D")
	end
end

function onDestroy()
	print("Destroying Bye Script")
end

function onUpdate(dt)
	print("Running Bye Script: ".. dt)

	local rb = entity:getComponent("RigidBody2DComponent")
	if rb ~= nil then
		--print(rb.mass)
		rb.mass = rb.mass - dt
		entity:setComponent("RigidBody2DComponent", rb)
	end
end
