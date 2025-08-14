function onUpdate(dt)
	print("Running Bye Script: ".. dt)

	local rb = entity:getComponent("RigidBody2DComponent")
	if rb ~= nil then
		--print(rb.mass)
		rb.mass = rb.mass - dt
		entity:setComponent("RigidBody2DComponent", rb)
	end
end
